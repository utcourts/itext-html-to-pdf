# UTCourts HTML to PDF REST API Service

A REST API service that accepts HTML content in the request payload and responds with the generated PDF content in binary form.  
This service is powered by **iText 7** with the **pdfHTML add-on** for HTML-to-PDF conversion.

---

## 🚀 Features
- Accepts raw HTML content as input (via REST API).
- Converts HTML to high-quality PDF using iText pdfHTML.
- Returns PDF content as a binary response.
- Easy to integrate with other services or applications.

---

## 📦 Installation
```bash
# Clone the repository

# Move into project folder
cd itext-html-pdf-oss

# Build the project (example for Maven)s
mvn clean install

# Run the service
mvn spring-boot:run

# Usage
curl -X POST "http://host-address/html-to-pdf/convert" \
           -H "Content-Type: multipart/form-data" \
           -F "file=@example.html" \
           --output output.pdf
```

- Input: A file containing the HTML content to convert.
- Output: Binary PDF file (written to output.pdf in this example).

---

## 📂 Project Structure
```
📦itext-html-pdf-oss
└── src
    └── main
        └── java # java source code
            └── gov.utcourts.oss.pdf.api
                └── controller # Controller
        └── 📂 resources # Configs, templates
    └── 📂 test # Unit and integration tests
├── 📄 pom.xml # Maven build file
├── 📄 README.md # Project documentation
├── 📄 LICENSE # License file (AGPL/iText notice)
```

---

## 🤝 Contribution

Contributions are welcome! Please open issues or submit pull requests.  
Make sure to follow the contribution guidelines if available.

---

## ⚖️ License

This project is licensed under the [GNU Affero General Public License v3.0 (AGPL-3.0)](https://www.gnu.org/licenses/agpl-3.0.html).  
See the [LICENSE](./LICENSE) file for details.

### Important iText Notice

This project uses the [iText PDF library](https://itextpdf.com/) for PDF generation.  
iText is licensed under the [GNU Affero General Public License (AGPL) v3.0](https://www.gnu.org/licenses/agpl-3.0.html).

#### What this means:
- If you distribute this project or make it available as a service, you must also make your **complete source code available** under AGPL v3.0.
- For closed-source or commercial use, you must obtain a **commercial license** from [iText Software](https://itextpdf.com/en/how-buy).


For more information, see:
- [AGPL v3.0 License Text](https://www.gnu.org/licenses/agpl-3.0.html)
- [iText License FAQ](https://itextpdf.com/en/how-buy/legal/itext-licenses)