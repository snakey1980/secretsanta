import java.io.File
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

data class Participant(val name: String, val email: String)

fun ArrayList<Participant>.pick(picker: Participant) : Participant {
    if (this.isEmpty()) {
        throw IllegalStateException("pot is empty")
    }
    Collections.shuffle(this)
    while (this[0] == picker && this.size > 1) {
        Collections.shuffle(this)
    }
    return this.removeAt(0)
}

fun draw(participants: List<Participant>) : Iterable<Pair<Participant, Participant>> {
    val pot = ArrayList(participants)
    return participants.map { participant -> Pair(participant, pot.pick(participant)) }
}


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
    val participants = File(partcipantsCsvFile).readLines().filter { line -> !line.isBlank() }.map { line -> Participant(line.split(",")[0], line.split(",")[1]) }
    var draw = draw(participants)
    while (draw.last().first == draw.last().second) {
        draw = draw(participants)
    }
    for ((giver, receiver) in draw) {
        println("$giver is giving to $receiver".format(giver, receiver))
        sendEmail(from, giver, "Secret Santa $year", "Hi ${giver.name}, this year you should buy a gift for ${receiver.name}.  This is an automated email and I have no knowledge of its contents", password)
    }
}

fun sendEmail(from: String, to: Participant, subject: String, body: String, password: String) {
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