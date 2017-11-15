package com.snakey.secretsanta

import java.io.File

/*
  arg 0: location of participants csv file with name,email pairs
  arg 1: year
  arg 2: from gmail address
  arg 3: gmail password
 */

fun main(args: Array<String>) {
    val partcipantsCsvFile = args[0]
    val year = args[1]
    val from = args[2]
    val password = args[3]
    val participants = File(partcipantsCsvFile).readLines().filter { line -> !line.isBlank() }.map { line -> Pair(line.split(",")[0], line.split(",")[1]) }
    SecretSanta().drawAndSendEmails(year, from, password, participants)
}