# Cloudbox 🌟

Cloudbox is a cloud file storage backend application built with Spring Boot, similar to Google Drive. It provides a RESTful API for file storage and management, allowing users to upload, download, list, and delete files, with metadata stored in a MySQL database and files saved to a local directory.

## Features 🚀

- **File Upload:** Upload files to the server, optionally specifying a parent folder.
- **File Download:** Download files by their unique ID.
- **List Files:** List all files or files within a specific folder.
- **Delete Files:** Delete files by their unique ID.
- **Metadata Storage:** Stores file metadata (name, path, size, type, parent folder, creation time) in a MySQL database.

## Tech Stack 💻

- **Java 21** ☕
- **Spring Boot 3.4.5** 🌱
- **Spring Data JPA** 📊
- **Spring Web** 🌐
- **MySQL** 🐬

## Project Structure 📁

```
cloudbox/
├── src/
│   ├── main/
│   │   ├── java/com/uddancode/cloudbox/
│   │   │   ├── controller/         # REST controllers (FileController)
│   │   │   ├── services/           # Business logic (FileServiceStorage)
│   │   │   ├── repo/               # JPA repositories (FileRepository)
│   │   │   ├── entity/             # JPA entities (FileEntity)
│   │   │   └── CloudboxApplication.java # Main entry point
│   │   └── resources/
│   │       └── application.properties # Configuration
├── pom.xml
```

## API Endpoints 🔗

- `POST /api/files/upload`  
  Upload a file. Parameters: `file` (multipart), `parentFolderId` (optional).

- `GET /api/files/download/{id}`  
  Download a file by its ID.

- `GET /api/files/list`  
  List all files or files in a folder. Parameter: `parentFolderId` (optional).

- `DELETE /api/files/delete/{id}`  
  Delete a file by its ID.

## Setup & Configuration ⚙️

1. **Clone the repository**

2. **Configure MySQL**  
   Ensure MySQL is running and update `src/main/resources/application.properties` if needed:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/driver_db
   spring.datasource.username=root
   spring.datasource.password=admin
   file.upload-dir=uploads/
   ```

3. **Build and Run**
   ```
   ./mvnw spring-boot:run
   ```

4. **Access the API**  
   The API will be available at `http://localhost:8080/api/files`.

## Dependencies 📦

See `pom.xml` for a full list, including:
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-web`
- `mysql-connector-java`

## Notes 📝

- File metadata is stored in the `files` table in MySQL.
- Uploaded files are saved to the directory specified by `file.upload-dir` (default: `uploads/`).
- CORS is enabled for `http://localhost:3000` (suitable for local frontend development).

## Additional Information ℹ️

- **Security:** Ensure that your MySQL credentials are secure and not exposed in production environments.
- **Performance:** For large file uploads, consider configuring your server to handle multipart requests efficiently.
- **Contributing:** Feel free to contribute to this project by submitting pull requests or reporting issues. 