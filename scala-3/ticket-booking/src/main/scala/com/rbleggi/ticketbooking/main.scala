package com.rbleggi.ticketbooking

case class Ticket(show: String, zone: String, seat: Int, date: String)

object PlaceCapacity:
  private var bookedSeats: Set[(String, String, Int, String)] = Set()

  def isAvailable(show: String, zone: String, seat: Int, date: String): Boolean =
    !bookedSeats.contains(show, zone, seat, date)

  def registerTicket(ticket: Ticket): Unit =
    bookedSeats += (ticket.show, ticket.zone, ticket.seat, ticket.date)

class TicketBookingService:
  def bookTicket(ticket: Ticket): Ticket =
    if !PlaceCapacity.isAvailable(ticket.show, ticket.zone, ticket.seat, ticket.date) then
      throw new IllegalArgumentException(
        s"Seat ${ticket.seat} in zone ${ticket.zone} for '${ticket.show}' on ${ticket.date} is already booked."
      )
    else
      PlaceCapacity.registerTicket(ticket)
      ticket

class TicketBuilder:
  private var show: String = ""
  private var zone: String = ""
  private var seat: Int = 0
  private var date: String = ""

  def setShow(show: String): TicketBuilder =
    this.show = show
    this

  def setZone(zone: String): TicketBuilder =
    this.zone = zone
    this

  def setSeat(seat: Int): TicketBuilder =
    this.seat = seat
    this

  def setDate(date: String): TicketBuilder =
    this.date = date
    this

  def buildTicket(): Ticket =
    Ticket(show, zone, seat, date)

@main def runTicketSystem(): Unit =
  val bookingService = new TicketBookingService

  try
    val ticket1 = new TicketBuilder()
      .setShow("Iron Maiden")
      .setZone("1")
      .setSeat(12)
      .setDate("2025-05-01")
      .buildTicket()

    val bookedTicket1 = bookingService.bookTicket(ticket1)
    println(s"Ticket booked: $bookedTicket1")

    val ticket2 = new TicketBuilder()
      .setShow("Iron Maiden")
      .setZone("1")
      .setSeat(12)
      .setDate("2025-05-01")
      .buildTicket()

    val bookedTicket2 = bookingService.bookTicket(ticket2)
    println(s"Ticket booked: $bookedTicket2")
  catch
    case e: Exception => println(s"Booking failed: ${e.getMessage}")
