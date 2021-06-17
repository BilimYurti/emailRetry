# Backend Coding Challenge: E-Mail Retry

## Description:

This Spring-Boot-Project consists the relevant parts of a microservice (Notification Service), that is responsible for sending notifications via different communication channels (E-Mail, SMS, Push Notification). The `EmailController` provides an HTTP interface, that can be used to send new emails.

In the current increment the transmission of emails is already performed asynchronously. Therefore the user of the interface receives an immediate `200 OK` status, in response to the HTTP request. The email is then *eventually* transmitted to the recipient. If an error occurs during the email delivery (e.g. because of a fault of the SMTP server) the user will **not** be notified of the failure and the notification will **not** be delivered.   

During the past weeks the Support Team received a large amount of complaints, stating that emails could not be delivered to the recipients. The cause was traced back to temporary disorders of the SMTP Server.

Being the new developer in the Notification Team, you receive the task to implement a retry mechanism for the transmission of emails. This should ensure that the messages are being successfully delivered to the recipients in the future, even during disorders of the SMTP Server.  

## Acceptance Criteria:

1. Per email notification there must be exactly 5 retry attempts. The first retry has to be made after 5 seconds, subsequent retrys must be executed in exponential time-displacement.
2. If the Notification Service crashes or is restarting during active retry processes, the retrys must continue with the state they were in before the reboot (e.g. 2 retrys successfully executed → Crash Notification Service → Restart Notification Service → Execute the remaining 3 retrys).
3. The implemented retry mechanism has to be developed for a multi-instance setup of the Notification Service. Please be aware of the fact during your architectural decisions.

## General Conditions:

- In the root directory of the project a `docker-compose.yml` can be found. By executing `docker-compose up` the required infrastructure for the challenge can be started in docker containers.
The following services are being started:
    - MongoDB (NoSQL Database): `[http://localhost:27017](http://localhost:27017)`
    - Mailhog (Mail Server Mock): `[http://localhost:1025](http://localhost:1025)` (SMTP Server), `[http://localhost:8025](http://localhost:8025)` (Web UI - Inbox)
    
    For successful completion of the challenge only the provided services may be used. You are not forced to use all of them, but it is not allowed to use additional services.

- The structure of the microservice at hand follows the Hexagonal-Architecture (aka. Clean Architecture - [https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)). Incorporate your implementation into the existing application architecture.
- By default the mail server mock (MailHog) is configured to reject 50% of the received emails. This behaviour can be modified or disabled in the `docker-compose.yml`.
- You should complete this coding challenge within `3` days. Please keep this guidance in mind and submit your (partial) result upon expiration of this time frame.
- If you draw up any notes or sketches during your implementation, please hand them in together with your solution.

## Bonus Tasks:

1. Write unit- and integration tests for your implemented retry mechanism.

---

## Beschreibung:

Dieses Spring-Boot-Projekt beinhaltet den relevanten Ausschnitt eines Microservices (Notification Service), der für den Versand von Benachrichtigungen über verschiedene Kommunikationskanäle (E-Mail, SMS, Push Notification) zuständig ist. Der `EmailController` stellt eine HTTP Schnittstelle zur Verfügung, über die sich eine neue Benachrichtigung per E-Mail versenden lässt.

In der aktuellen Ausbaustufe wird der E-Mail Versand bereits asynchron ausgeführt. Als Antwort auf den HTTP Request erhält der Benutzer der Schnittstelle somit unmittelbar einen Status `200 OK` und die E-Mail wird dem Empfänger daraufhin *irgendwann* zugestellt. Tritt während der Zustellung ein Fehler auf (z. B. der SMTP Server hat eine Störung und antwortet nicht) wird der Benutzer **nicht** informiert und die Benachrichtigung wird **nicht** übermittelt. 

In den letzten Wochen sind vermehrt Beschwerden über nicht zugestellt E-Mails beim Support Team eingegangen. Die Ursache ließ sich auf temporäre Ausfälle des SMTP Servers zurückführen.

Als neuer Entwickler im Notification Team erhältst du deshalb die Aufgabe einen Retry-Mechanismus für den E-Mail Versand zu implementieren. So soll sichergestellt werden, dass die Nachrichten zukünftig auch im Falle einer kurzzeitigen Störung des SMTP Servers erfolgreich an den Empfänger übermittelt werden.

## Akzeptanzkriterien

1. Pro E-Mail Benachrichtigung muss es genau 5 Versuche für die Zustellung geben. Der erste Versuch muss nach 5 Sekunden erfolgen und darauffolgende Retrys müssen in exponentiellem Abstand voneinander ausgeführt werden.
2. Startet der Notification Service neu, während einem oder mehreren aktiven Retry-Prozessen, müssen die Retrys nach dem Neustart der Anwendung an der Stelle fortgesetzt werden an der diese unterbrochen wurden (z. B.: 2 Retrys erfolgreich ausgeführt → Crash Notification Service → Neustart Notification Service → Ausführung der verbleibenden 3 Retrys).
3. Der implementierte Retry-Mechanismus muss für ein hochverfügbares Setup des Notification Service (d. h. mit mehreren parallel ausgeführten Instanzen) entwickelt werden. Beachte diesen Umstand bei deinen Architekturentscheidungen.

## Rahmenbedingungen:

- Im Hauptverzeichnis des Projekts liegt eine `docker-compose.yml`. Mit dem Befehl `docker-compose up` lässt sich die benötigte Infrastruktur für die Challenge in Docker starten. 
Dabei werden folgende Dienste bereitgestellt:
    - MongoDB (NoSQL Datenbank): `[http://localhost:27017](http://localhost:27017)`
    - Mailhog (Mail Server Mock): `[http://localhost:1025](http://localhost:1025)` (SMTP Server), `[http://localhost:8025](http://localhost:8025)` (Web UI - Inbox)

    Zur erfolgreichen Fertigstellung der Challenge dürfen ausschließlich die aufgelisteten Dienste verwendet werden. Die Verwendung dieser Dienste ist nicht verpflichtend, aber es dürfen keine zusätzlichen Anwendungen eingesetzt werden.

- Die Struktur des vorliegenden Microservices folgt der Hexagonalen-Architektur (aka. Clean Architecture - [https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)). Halte dich bei deiner Implementierung an die bestehende Anwendungsarchitektur.
- Standardmäßig ist der Mail Server Mock (Mailhog) so konfiguriert, dass nur 50% der E-Mails erfolgreich verarbeitet werden. Dieses Verhalten lässt sich über die `docker-compose.yml` anpassen oder komplett deaktivieren.
- Für diese Coding Challenge haben wir `3` Tage eingeplant. Bitte richte dich bei deiner Umsetzung nach diesem Richtwert und reiche dein (Teil-)Ergebnis nach Ablauf dieser Arbeitszeit ein.
- Solltest du während der Bearbeitung Zeichnungen oder Notizen anfertigen, reiche diese zusammen mit deiner Lösungen ein.

## Bonus Aufgaben:

1. Schreibe Unit- und Integrationstests für deinen implementierten Retry-Mechanismus.
