# **Social Media Sharing Photo App**

## **Overview**

A modular social media photo sharing PoC in Scala 3, demonstrating core features (publish, tag, timeline, comments) and event-driven notifications using the **Observer Pattern**. Followers are notified when users publish, tag, or comment on photos.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Observer Pattern** → Decouples user actions from notification logic
- **Photo Publishing** → Users can publish photos to their timeline
- **Tagging** → Tag users in photos
- **Timeline** → View a timeline of published photos and activities
- **Comments** → Add and view comments on photos
- **Notification System** → Followers receive notifications for new photos, tags, and comments
- **Easily Extensible** → Add new event types or observers without changing core logic

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class Subject {
        <<trait>>
        +registerObserver(observer: Observer): Unit
        +removeObserver(observer: Observer): Unit
        +notifyObservers(event: Event): Unit
    }

    class Observer {
        <<trait>>
        +update(event: Event): Unit
    }

    class User {
        -name: String
        -observers: List[Observer]
        -timeline: List[Photo]
        +publishPhoto(photo: Photo): Unit
        +tagUser(photo: Photo, user: String): Unit
        +commentPhoto(photo: Photo, comment: Comment): Unit
        +registerObserver(observer: Observer): Unit
        +removeObserver(observer: Observer): Unit
        +notifyObservers(event: Event): Unit
    }

    class Follower {
        -followerName: String
        +update(event: Event): Unit
    }

    class Photo {
        +id: String
        +url: String
        +tags: List[String]
    }

    class Comment {
        +user: String
        +text: String
    }

    Subject <|.. User
    Observer <|.. Follower
    User o-- Photo
    User o-- Comment
    User --> Observer : notifies
```

---

## **Observer Pattern**

The **Observer Pattern** allows the app to notify followers automatically when a user publishes, tags, or comments on a photo. This decouples the notification logic from user actions, making the system modular and extensible.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/social-media-photo-app
```

### **2️ - Compile & Run the Application**

```bash
./sbtw compile run
```

### **3️ - Run Tests**

```bash
./sbtw test
```

---

## **License**

MIT
