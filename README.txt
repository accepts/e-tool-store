
E-TOOLSTORE (ONLINE SHOP OF INDUSTRIAL TOOLS)
=============================================

This project is my result of consolidating in practice the study of some popular Java EE frameworks:

    Back-end:
    Spring Web-MVC, Spring Security (integrated with Thymeleaf Security and Spring DATA JPA),
    Maven, Apache commons (photo upload), Javax Mail, Log4j (integrated with GSON).

    Database layer:
    - PostgreSQL;
    - Spring DATA JPA;

    Front-end:
    - CSS, Bootstrap, FontAwesome icons, Photoshop;
    - HTML pages with Thymeleaf and Thymeleaf Security tags;
    - AngularJS (for validation user’s input and for invoke REST methods from Spring-MVC controller);


THE BRIEF DESCRIPTION OF THE “E-TOOLSTORE” PROJECT:
--------------------------------------------------

Internet shop has Users with different roles and possibilities:
customers and managerial personal (users and administrator). Customers can register, sign in,
operate with products items in their current cart, communicate with managerial personal via email.
Users and Administrator can operate with Products (CRUD, change status), Orders (Edit, Delete,
Change current status), and only Administrator have permission to deal with Users (Lock, Unlock,
Delete). Also exist a possibility to search and sorting products of some parameters
during the browsing.


ESSENTIAL INFORMATION FOR LAUNCH THIS PROJECT
---------------------------------------------

Find file and fill next necessary lines.

    [project_folder]\src\main\resources\messages.properties

System settings

    path.to.photo.storage = path to folder for storage your products photos
    max.picture.file.size.in.megabytes = maximum size of photo file in Mb
    product.item.per.page = how much item must by displayed at one page
    product.item.per.page.admin = how much item must by displayed at administrator tables on one page
    product.search.sort.by = default sorting (by price or manufacturer)

Database connection parameters (PostgerSQL):

    db.url = URL to database
    db.username = database username
    db.password = database password

E-mail settings for sanding messages:

    email.host = smtp.gmail.com
    email.port = 587
    email.login = your email login
    email.password = your email password
    email.domain = @gmail.com

__________________________________________________________________________
Created in StackEdit ; april 2017; made by obelets.sergey@gmail.com