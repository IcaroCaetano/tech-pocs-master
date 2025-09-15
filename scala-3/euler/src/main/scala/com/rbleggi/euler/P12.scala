package com.rbleggi.euler

// Project Euler Problem 12: What is the value of the first triangle number to have over five hundred divisors?
// This program finds the first triangle number with more than 500 divisors.
def countDivisors(n: Long): Int =
  var num = n
  var count = 1
  var p = 2L
  while p * p <= num do
    var exp = 0
    while num % p == 0 do
      num /= p
      exp += 1
    if exp > 0 then count *= (exp + 1)
    p = if p == 2 then 3 else p + 2
  if num > 1 then count *= 2
  count

def firstTriangleWithDivisors(limit: Int): Long =
  var n = 1L
  var triangle = 1L
  while countDivisors(triangle) <= limit do
    n += 1
    triangle += n
  triangle

@main def runP12(): Unit =
  println(s"The first triangle number with over 500 divisors is: ${firstTriangleWithDivisors(500)}")
