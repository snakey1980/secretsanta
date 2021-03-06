package com.snakey.secretsanta

import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SecretSanta {

    internal fun drawAndSendEmails(year: String, from: String, password: String, participants: List<Pair<String, String>>) {
        if (participants.size < 4) {
            throw IllegalArgumentException("Not enough participants (must have at least four)")
        }
        val draw = draw(participants.map { p -> Participant(p.first, p.second) })
        for ((giver, receiver) in draw) {
                sendEmail(from, giver, "Valuations Secret Santa $year", "Hi ${giver.name}, this year you should buy a gift for ${receiver.name}.  This is an automated email and I have no knowledge of its contents", password)
        }
    }

    internal data class Participant(val name: String, val email: String)

    internal fun draw(participants: List<Participant>) : List<Pair<Participant, Participant>> {
        if (participants.size < 4) throw IllegalArgumentException()
        val permutation = participants.toMutableList()
        do Collections.shuffle(permutation)
        while ((0 until participants.size).any { participants[it] == permutation[it] })
        return (0 until participants.size).map { Pair(participants[it], permutation[it]) }
    }

    private fun sendEmail(from: String, to: Participant, subject: String, body: String, password: String) {
        println("Sending an email to ${to.name} (${to.email})")
        val props = Properties()
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smtp.port", "587")
        val session = Session.getInstance(props, null)
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(from))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.email))
        message.setSubject(subject)
        message.setText(body)
        val transport = session.getTransport("smtp")
        transport.connect(from, password)
        transport.sendMessage(message, message.allRecipients)
        transport.close()
    }

}