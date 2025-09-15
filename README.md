# 🚀 Tech POCs

Welcome to the **Tech POCs repository**!  
This repository contains **Proof of Concept (POC) projects** demonstrating different technologies and architectures.

Each POC is **self-contained**, with its own dependencies, build configurations, and documentation.

---

## 📌 Implementations

| 🧩 System                     | 📄 Description                                                                                                                                                                              | 📘 [Pattern](https://refactoring.guru/) | 🛠️ Implementations                                                                                                            |
|-------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| 🎯 **Tax System**             | Build a TAX system where different products have different tax per state and year.                                                                                                          | Specification                           | 🟢 **Kotlin**📘 [README](kotlin/tax-system/README.md)<br>🔵 **Scala 3**📘 [README](scala-3/tax-system/README.md)               |
| 🧭 **Logger Router**          | Build a Logger Builder Router System where you can log into FS, ELK or any other log system where you can configure the logs to be sync or async using the same API.                        | Strategy                                | 🔵 **Scala 3**📘 [README](scala-3/logger-router/README.md)                                                                     |
| 🚚 **Logistic Pricing**       | Build A Logistic System where you need to calculate different freight prices based on volume, size and type of transportation i.e boat, truck, rail, prices are dynamic they keep changing. | Strategy                                | 🔵 **Scala 3**📘 [README](scala-3/logistic-pricing/README.md)                                                                  |
| 🍽 **Restaurant Queue**       | Build Restaurant Queue System Capable of telling how long each dish will take.                                                                                                              | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/restaurant-queue/README.md)                                                                  |
| 🎟 **Ticket Booking**         | Build Ticket system, where you should be able to sell x number of ticker per different shows and choose the seats number, zone of the venue, date and respect maximum capacity.             | Builder                                 | 🔵 **Scala 3**📘 [README](scala-3/ticket-booking/README.md)                                                                    |
| 🖌️ **Template Renderer**     | Build Render Template where the same temple can be render in HTML, PDF or CSV.                                                                                                              | Factory                                 | 🟢 **Kotlin**📘 [README](kotlin/template-renderer/README.md)<br>🔵 **Scala 3**📘 [README](scala-3/template-renderer/README.md) |
| 🎸 **Guitar Factory**         | Build Guitar Factory System where you can specify details of a guitar and the system creates a custom guitar for you with OS, Specs, Models. The system should keep track of Inventory.     | Builder                                 | 🔵 **Scala 3**📘 [README](scala-3/guitar-factory/README.md)                                                                    |
| 🎬 **Movie Recommender**      | Movie recommender engine using The Movies Dataset from Kaggle.                                                                                                                              |                                         | 🟢 **Python**📘 [README](python/movie-recommender/README.md)                                                                   |
| 🛒 **Grocery TODO List**      | Build a Grocery TODO List system (add item, remove, mark as done, do, re-do, listAll).                                                                                                      | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/grocery-todo-list/README.md)                                                                 |
| 📂 **File Share**             | Build a FileShare System (save files, restore files, delete files, listFiles, Search) with encryption.                                                                                      | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/file-share/README.md)                                                                        |
| 🛡️ **Dungeon Game**          | Solve the Dungeon Game problem where a knight must rescue a princess while minimizing the initial health required to survive.                                                               | Strategy                                | 🔵 **Scala 3**📘 [README](scala-3/dungeon-game/README.md)                                                                      |
| 📝 **Note Taking**            | Build a Note Taking system (add notes,save notes,edit notes, delete notes, sync).                                                                                                           | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/note-taking/README.md)                                                                       |
| 📅 **Calendar**               | Build a Calendar system (book meetings, remove meetings, listMeetings, suggest best time for 2 people).                                                                                     | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/calendar/README.md)                                                                          |
| 👨‍🏫 **Class Organizer**     | Build a Teacher's Class Organizer/Optimizer.                                                                                                                                                | Observer                                | 🔵 **Scala 3**📘 [README](scala-3/class-organizer/README.md)                                                                   |
| 🗄️ **Redis Clone System**    | Build a Redis clone client/server (Strings: set,get,remove,append, maps: set,get,keys,values).                                                                                              | Command                                 | 🔵 **Scala 3**📘 [README](scala-3/redis-clone/README.md)                                                                       |
| 📷 **Social Media Photo App** | Build a Social Media Sharing Photo App (publish photos, tag photos, timeline, comments).                                                                                                    | Observer                                | 🔵 **Scala 3**📘 [README](scala-3/social-media-photo-app/README.md)                                                            |
| 🔄 **Converter Framework**    | Build a converter Framework where you convert complex types.                                                                                                                                | Adapter                                 | 🔵 **Scala 3**📘 [README](scala-3/converter-framework/README.md)                                                               |

---

## 📚 Scala Study Materials

In addition to the POCs, this repository also includes a dedicated folder for studying and exploring Scala. The
`scala-study` directory contains learning notes and guides ranging from basics to advanced topics, libraries, and Spring
integration.

| 🗂️ Topic        | 📄 Description                                    | 🔗 Link                                              |
|------------------|---------------------------------------------------|------------------------------------------------------|
| 📘 **Index**     | Overview and entry point for all Scala materials  | [README.md](scala-study/README.md)                   |
| 🟡 **Basics**    | Fundamental concepts of the Scala language        | [README-Basics.md](scala-study/README-Basics.md)     |
| 🟠 **Advanced**  | Advanced techniques, patterns, and concepts       | [README-Advanced.md](scala-study/README-Advanced.md) |
| 🔵 **Libraries** | Useful libraries and tools in the Scala ecosystem | [README-Libs.md](scala-study/README-Libs.md)         |
| 🟢 **Spring**    | Using Scala with the Spring Framework             | [README-Spring.md](scala-study/README-Spring.md)     |
