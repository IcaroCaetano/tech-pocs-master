package com.rbleggi.calendar

import java.time.{LocalDateTime, LocalTime, format}
import java.time.format.DateTimeFormatter

case class User(id: String, name: String)

case class Meeting(id: String, title: String, start: LocalDateTime, end: LocalDateTime, attendees: Set[User])

class Calendar:
  private var meetings: Map[String, Meeting] = Map.empty

  def bookMeeting(meeting: Meeting): Boolean =
    if meetings.values.exists(m =>
      m.attendees.intersect(meeting.attendees).nonEmpty && m.start.isBefore(meeting.end) && meeting.start.isBefore(m.end)
    ) then false
    else
      meetings += (meeting.id -> meeting)
      true

  def removeMeeting(meetingId: String): Boolean =
    if meetings.contains(meetingId) then
      meetings -= meetingId
      true
    else false

  def listMeetings(user: User): List[Meeting] =
    meetings.values.filter(_.attendees.contains(user)).toList

  def getMeetingsForUsers(users: Set[User]): List[Meeting] =
    meetings.values.filter(m => m.attendees.intersect(users).nonEmpty).toList

  def getAllMeetings: List[Meeting] = meetings.values.toList

trait CalendarCommand:
  def execute(): Any

class BookMeetingCommand(calendar: Calendar, meeting: Meeting) extends CalendarCommand:
  def execute(): Boolean = calendar.bookMeeting(meeting)

class RemoveMeetingCommand(calendar: Calendar, meetingId: String) extends CalendarCommand:
  def execute(): Boolean = calendar.removeMeeting(meetingId)

class ListMeetingsCommand(calendar: Calendar, user: User) extends CalendarCommand:
  def execute(): List[Meeting] = calendar.listMeetings(user)

class SuggestBestTimeCommand(calendar: Calendar, user1: User, user2: User, durationMinutes: Long) extends CalendarCommand:
  def execute(): Option[(LocalDateTime, LocalDateTime)] =
    SuggestionEngine.suggestBestTime(calendar, user1, user2, durationMinutes)

class CalendarInvoker:
  def execute(command: CalendarCommand): Any = command.execute()

object SuggestionEngine:
  def suggestBestTime(calendar: Calendar, user1: User, user2: User, durationMinutes: Long): Option[(LocalDateTime, LocalDateTime)] =
    val now = LocalDateTime.now()
    val endOfDay = now.`with`(LocalTime.MAX)
    val meetings = calendar.getMeetingsForUsers(Set(user1, user2))
    val busySlots = meetings.map(m => (m.start, m.end)).sortBy(_._1)
    var slotStart = now
    while slotStart.plusMinutes(durationMinutes).isBefore(endOfDay) do
      val slotEnd = slotStart.plusMinutes(durationMinutes)
      val overlaps = busySlots.exists { case (start, end) =>
        slotStart.isBefore(end) && slotEnd.isAfter(start)
      }
      if !overlaps then return Some((slotStart, slotEnd))
      slotStart = slotStart.plusMinutes(15)
    None

@main def runCalendarDemo(): Unit =
  val calendar = new Calendar
  val invoker = new CalendarInvoker
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  println("===== Calendar System Demo =====")
  
  val user1 = User("1", "Alice")
  val user2 = User("2", "Bob")
  val user3 = User("3", "Charlie")
  
  println(s"Created users: ${user1.name}, ${user2.name}, ${user3.name}\n")

  val meeting1 = Meeting("m1", "Sync", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), Set(user1, user2))
  val result1 = invoker.execute(BookMeetingCommand(calendar, meeting1)).asInstanceOf[Boolean]
  println(s"Book meeting '${meeting1.title}' (${meeting1.start.format(formatter)} - ${meeting1.end.format(formatter)}): ${if (result1) "SUCCESS" else "FAILED"}")

  val conflictingMeeting = Meeting("m2", "Conflict", LocalDateTime.now().plusHours(1).plusMinutes(30),
    LocalDateTime.now().plusHours(2).plusMinutes(30), Set(user1, user3))
  val result2 = invoker.execute(BookMeetingCommand(calendar, conflictingMeeting)).asInstanceOf[Boolean]
  println(s"Book meeting '${conflictingMeeting.title}' (${conflictingMeeting.start.format(formatter)} - ${conflictingMeeting.end.format(formatter)}): ${if (result2) "SUCCESS" else "FAILED (Conflict detected)"}")

  val meeting3 = Meeting("m3", "Planning", LocalDateTime.now().plusHours(3),
    LocalDateTime.now().plusHours(4), Set(user1, user2, user3))
  val result3 = invoker.execute(BookMeetingCommand(calendar, meeting3)).asInstanceOf[Boolean]
  println(s"Book meeting '${meeting3.title}' (${meeting3.start.format(formatter)} - ${meeting3.end.format(formatter)}): ${if (result3) "SUCCESS" else "FAILED"}\n")

  val aliceMeetings = invoker.execute(ListMeetingsCommand(calendar, user1)).asInstanceOf[List[Meeting]]
  println(s"${user1.name}'s meetings (${aliceMeetings.size}):")
  aliceMeetings.foreach(m => println(s"  • ${m.title} at ${m.start.format(formatter)} with ${m.attendees.size} attendees"))
  println()

  val suggestedTime = invoker.execute(SuggestBestTimeCommand(calendar, user1, user2, 30)).asInstanceOf[Option[(LocalDateTime, LocalDateTime)]]
  println("Suggest best time for Alice and Bob (30min):")
  suggestedTime match
    case Some((start, end)) => println(s"  Available slot found: ${start.format(formatter)} - ${end.format(formatter)}")
    case None => println("  No available time slots found today")
  println()

  val removeResult = invoker.execute(RemoveMeetingCommand(calendar, "m1")).asInstanceOf[Boolean]
  println(s"Remove meeting 'Sync': ${if (removeResult) "SUCCESS" else "FAILED"}\n")

  val aliceMeetingsAfter = invoker.execute(ListMeetingsCommand(calendar, user1)).asInstanceOf[List[Meeting]]
  println(s"${user1.name}'s meetings after removal (${aliceMeetingsAfter.size}):")
  aliceMeetingsAfter.foreach(m => println(s"  • ${m.title} at ${m.start.format(formatter)} with ${m.attendees.size} attendees"))
