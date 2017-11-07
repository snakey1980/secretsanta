import java.io.File
import java.util.*
import java.util.*
import javax.mail.*
import javax.mail.internet.*



data class Participant(val name: String, val email: String)

fun ArrayList<Participant>.pick(picker: Participant) : Participant {
    if (this.isEmpty()) {
        throw IllegalStateException("pot is empty")
    }
    if (this.size == 1 && this[0] == picker) {
        return this[0]
    }
    Collections.shuffle(this)
    while (this[0] == picker) {
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
  arg 3: gmail username
  arg 4: gmail password
 */

fun main(args: Array<String>) {
    val partcipantsCsvFile = args[0]; val year = args[1]; val from = args[2]; val username = args[3]; val password = args[4]
    val participants = File(partcipantsCsvFile).readLines().filter { line -> !line.isBlank() }.map { line -> Participant(line.split(",")[0], line.split(",")[1]) }
    var draw = draw(participants)
    while (draw.last().first == draw.last().second) {
        draw = draw(participants)
    }
    for ((giver, receiver) in draw) {
        println("$giver is giving to $receiver".format(giver, receiver))
        sendEmail(from, giver.email, "Secret Santa $year", "Hi $giver, this year you should buy a gift for $receiver.  This is an automated email and I have no knowledge of its contents", username, password)
    }
}

fun sendEmail(from: String, to: String, subject: String, body: String, user: String, password: String) {
    val props = Properties()
    props.put("mail.smtp.auth", "true")
    props.put("mail.smtp.starttls.enable", "true")
    props.put("mail.smtp.host", "smtp.gmail.com")
    props.put("mail.smtp.port", "587")
    val session = Session.getInstance(props, null)
    val message = MimeMessage(session)
    message.setFrom(InternetAddress(from))
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
    message.setSubject(subject)
    message.setText(body)
    val transport = session.getTransport("smtp")
    transport.connect(from, password)
    transport.sendMessage(message, message.allRecipients)
    transport.close()
}