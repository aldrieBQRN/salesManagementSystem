# Sales Management System

A desktop-based Sales Management System built in Java Swing. This application provides a graphical user interface (GUI) for managing products, sales, users, and salespeople. It features separate dashboards and functionalities for Administrators and Salespersons.

This project was developed using the **Apache NetBeans IDE** and uses **Apache Ant** as its build tool.

## Key Features

### Administrator Dashboard
* **User Management:** Add, update, and delete system users.
* **Product Management:** Add new products, update existing product details, and manage stock levels.
* **Salesperson Management:** Create, view, and manage salesperson accounts.
* **Sales Tracking:** View and track all sales transactions made by salespeople.
* **Home Dashboard:** A central navigation panel for all admin functions.

### Salesperson Dashboard
* **Login Portal:** Secure login for salespersons.
* **View Products:** Browse and search available products.
* **Create Purchase:** Add products to a cart and complete a purchase/sales transaction.
* **View Sales:** View personal sales history.
* **Home Dashboard:** A central navigation panel for all salesperson functions.

## Technology Stack
* **Language:** Java
* **Framework:** Java Swing (for the GUI)
* **Build Tool:** Apache Ant (as seen in `build.xml`)
* **IDE:** Developed using Apache NetBeans (identified by `nbproject` folder)

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 8 or higher
* Apache NetBeans IDE (recommended)

### Running the Project

1.  **Clone the Repository**
    ```bash
    git clone [URL_OF_YOUR_REPOSITORY]
    ```
2.  **Open in NetBeans**
    * Open Apache NetBeans.
    * Go to `File` > `Open Project...`.
    * Navigate to and select the `salesManagement` folder (which contains the `nbproject` directory).
    * NetBeans will automatically recognize and load the project.

3.  **Database Setup (Important)**
    * This application requires a database to store user, product, and sales data.
    * Check the Java source files (e.g., `Login.java` or a database connection class) for the JDBC connection string, database name, and table requirements.
    * You must set up this database and its tables before the application can run successfully.

4.  **Build and Run**
    * Right-click on the `salesManagement` project in the NetBeans project explorer.
    * Select **"Clean and Build"**. This will compile the code and create a runnable `.jar` file in the `dist/` directory.
    * Right-click on the project again and select **"Run"**. This will start the application and open the `Login` window.

## Default Login

* **Admin:** Check the `Login.java` file or database for default administrator credentials (e.g., `admin` / `admin`).
* **Salesperson:** Log in using credentials created in the Admin Dashboard.
