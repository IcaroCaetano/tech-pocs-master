package com.rbleggi.dungeongame

@main def main(): Unit = {
  val dungeons = Seq(
    Array(
      Array(-2, -3, 3),
      Array(-5, -10, 1),
      Array(10, 30, -5)
    ),
    Array(
      Array(-2, -3, 3, 5), 
      Array(-5, -10, 1, 9),
      Array(10, 30, -5, 7)
    ),
    Array(Array(0)) 
  )

  def calculateMinimumHP(dungeon: Array[Array[Int]]): Int = {
    val rows = dungeon.length // Number of rows in the dungeon
    val cols = dungeon.headOption.map(_.length).getOrElse(0) // Number of columns in the dungeon
    if (rows == 0 || cols == 0) return 1 // If the dungeon is empty, return 1

    // Create a table to store the minimum health required at each cell
    val healthTable = Array.fill(rows, cols)(0)
    // Calculate the health required at the bottom-right cell
    healthTable(rows - 1)(cols - 1) = Math.max(1, 1 - dungeon(rows - 1)(cols - 1))

    // Fill the last column from bottom to top
    for (i <- (rows - 2 to 0 by -1))
      healthTable(i)(cols - 1) = Math.max(1, healthTable(i + 1)(cols - 1) - dungeon(i)(cols - 1))

    // Fill the last row from right to left
    for (j <- (cols - 2 to 0 by -1))
      healthTable(rows - 1)(j) = Math.max(1, healthTable(rows - 1)(j + 1) - dungeon(rows - 1)(j))

    // Fill the rest of the table from bottom-right to top-left
    for {
      i <- (rows - 2 to 0 by -1) // Iterate rows from bottom to top
      j <- (cols - 2 to 0 by -1) // Iterate columns from right to left
    } healthTable(i)(j) = Math.max(1, Math.min(healthTable(i + 1)(j), healthTable(i)(j + 1)) - dungeon(i)(j))

    healthTable(0)(0) // Return the minimum health required at the start
  }

  dungeons.foreach { dungeon =>
    println(s"Minimum initial health needed: ${calculateMinimumHP(dungeon)}")
  }
}
