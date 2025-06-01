# 📱 ToDo List Mobile App - Automation Testing

> **UAS Kualitas Perangkat Lunak**  
> Automation Testing menggunakan Appium + Java + TestNG

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/yourusername/todo-list-automation-testing)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Appium](https://img.shields.io/badge/Appium-8.6.0-blue.svg)](https://appium.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-red.svg)](https://testng.org/)

---

## 📖 Deskripsi Project

Project ini merupakan implementasi automation testing untuk aplikasi mobile **ToDo List** berbasis Android. Automation testing dibuat menggunakan framework **Appium** dengan bahasa pemrograman **Java** dan testing framework **TestNG**. Project ini bertujuan untuk memvalidasi fungsionalitas utama aplikasi ToDo List melalui automated test cases yang komprehensif.

## 📱 Aplikasi Yang Ditest

**ToDo List Mobile App** adalah aplikasi Android sederhana yang memungkinkan pengguna untuk:
- ✅ Menambahkan task baru
- ✅ Menandai task sebagai completed/incomplete
- ✅ Menghapus task individual
- ✅ Menghapus semua task sekaligus
- ✅ Melihat counter total task dan completed task
- ✅ Validasi input (duplicate task, empty task)

### Fitur Aplikasi:
- **Add Task**: Input field dengan tombol ADD untuk menambah task baru
- **Task List**: ListView menampilkan semua task dengan checkbox dan tombol delete
- **Task Counter**: Menampilkan total task dan jumlah task yang sudah completed
- **Clear All**: Tombol untuk menghapus semua task dengan konfirmasi dialog
- **Validation**: Validasi untuk input kosong dan duplicate task

## 🧪 Test Cases

### 1. **App Launch Verification** 
Memverifikasi aplikasi dapat diluncurkan dengan sukses dan semua elemen UI utama ditampilkan dengan benar.

### 2. **Add New Task**
Testing penambahan task baru dan verifikasi task muncul di list dengan counter yang terupdate.

### 3. **Add Multiple Tasks**
Testing penambahan beberapa task sekaligus untuk memastikan aplikasi dapat handle multiple entries.

### 4. **Mark Task as Completed**
Testing fungsionalitas checkbox untuk menandai task sebagai completed dan verifikasi completed counter.

### 5. **Delete Specific Task**
Testing penghapusan task individual dengan konfirmasi dialog dan verifikasi task terhapus dari list.

### 6. **Delete Task - Cancel Operation**
Testing pembatalan operasi delete task melalui dialog konfirmasi.

### 7. **Clear All Tasks**
Testing fungsionalitas clear all dengan konfirmasi dialog dan verifikasi semua task terhapus.

### 8. **Empty Task Input Validation**
Testing validasi input kosong - memastikan task kosong tidak dapat ditambahkan.

### 9. **Duplicate Task Validation**
Testing validasi duplicate task - memastikan task yang sama tidak dapat ditambahkan dua kali.

### 10. **Task Counter Accuracy**
Testing akurasi counter untuk total task dan completed task dalam berbagai skenario.

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Appium** | 8.6.0 | Mobile Automation Framework |
| **Selenium** | 4.15.0 | WebDriver Foundation |
| **TestNG** | 7.8.0 | Testing Framework |
| **Maven** | 3.x | Build & Dependency Management |
| **ExtentReports** | 5.0.9 | Test Reporting |
| **Android Studio** | Latest | IDE & Emulator |

## 📁 Struktur Project

```
todo-list-automation-testing/
├── automation-test/                 # Maven Test Project
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/example/tests/
│   │   │       ├── base/
│   │   │       │   └── BaseTest.java          # Base test configuration
│   │   │       ├── pages/
│   │   │       │   └── ToDoPage.java          # Page Object Model
│   │   │       ├── utils/
│   │   │       │   └── TestUtils.java         # Utility functions
│   │   │       └── listeners/
│   │   │           └── TestListener.java      # Test event listeners
│   │   └── test/
│   │       ├── java/
│   │       │   └── ToDoListTest.java          # Main test class
│   │       └── resources/
│   │           └── testng.xml                 # TestNG configuration
│   ├── test-output/                           # Test reports & screenshots
│   │   ├── screenshots/                       # Failure screenshots
│   │   └── ExtentReport.html                  # HTML test report
│   └── pom.xml                                # Maven dependencies
├── todolist/                                  # Android App Project
│   ├── apk/
│   │   └── to-do-list.apk                     # Application APK file
│   └── app/                                   # Android source code
└── README.md                                  # Project documentation
```

## ⚙️ Prerequisites & Installation

### 1. **Java Development Kit (JDK)**
```bash
# Install JDK 21
sudo apt update
sudo apt install openjdk-21-jdk

# Verify installation
java -version
javac -version
```

### 2. **Android Studio & SDK**
1. Download dan install [Android Studio](https://developer.android.com/studio)
2. Install Android SDK melalui SDK Manager
3. Setup Android emulator atau connect physical device
4. Enable USB Debugging pada device

### 3. **Appium Server**
```bash
# Install Node.js
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt-get install -y nodejs

# Install Appium
npm install -g appium@next

# Install UiAutomator2 driver
appium driver install uiautomator2

# Verify installation
appium -v
appium driver list
```

### 4. **Maven**
```bash
# Install Maven
sudo apt install maven

# Verify installation
mvn -version
```

### 5. **Environment Variables**
Tambahkan ke `~/.bashrc` atau `~/.zshrc`:
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

## 🚀 Cara Menjalankan Test

### 1. **Clone Repository**
```bash
git clone https://github.com/yourusername/todo-list-automation-testing.git
cd todo-list-automation-testing
```

### 2. **Setup Android Device/Emulator**
```bash
# Start Android emulator
emulator -avd YOUR_AVD_NAME

# Atau verify connected device
adb devices
```

### 3. **Start Appium Server**
```bash
# Start Appium server
appium

# Server akan berjalan di http://127.0.0.1:4723
```

### 4. **Install App Dependencies**
```bash
cd automation-test
mvn clean install
```

### 5. **Update Test Configuration**
Edit file `src/test/resources/testng.xml`:
```xml
<parameter name="deviceName" value="YOUR_DEVICE_NAME"/>
<parameter name="platformVersion" value="YOUR_ANDROID_VERSION"/>
```

### 6. **Run Tests**
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=ToDoListTest

# Run with custom parameters
mvn test -DdeviceName=emulator-5554 -DplatformVersion=11
```

## 📊 Test Reports

### Test Execution Results
Setelah menjalankan test, hasil dapat dilihat di:

1. **Console Output**: Real-time test execution logs
2. **TestNG Reports**: `target/surefire-reports/index.html`
3. **ExtentReports**: `test-output/ExtentReport.html`
4. **Screenshots**: `test-output/screenshots/` (untuk failed tests)

### Sample Test Results
```
===============================================
ToDoList Automation Test Suite
Total tests run: 10, Passes: 10, Failures: 0, Skips: 0
===============================================

✅ testAppLaunch: PASSED
✅ testAddNewTask: PASSED  
✅ testAddMultipleTasks: PASSED
✅ testMarkTaskCompleted: PASSED
✅ testDeleteSpecificTask: PASSED
✅ testDeleteTaskCancel: PASSED
✅ testClearAllTasks: PASSED
✅ testEmptyTaskValidation: PASSED
✅ testDuplicateTaskValidation: PASSED
✅ testTaskCounterAccuracy: PASSED
```

## 🏗️ Design Patterns & Best Practices

### **Page Object Model (POM)**
- Implementasi POM pattern dalam `ToDoPage.java`
- Separation of concern antara test logic dan page elements
- Reusable page methods untuk interaction dengan UI elements

### **Test Data Management**
- Parameterized test configuration melalui TestNG
- Environment-specific configurations
- Test data isolation antar test cases

### **Error Handling & Logging**
- Comprehensive error handling dengan try-catch blocks
- Detailed logging untuk debugging dan monitoring
- Screenshot capture pada test failures

### **Reporting & Documentation**
- ExtentReports untuk HTML test reports
- TestNG native reporting
- Screenshot evidence untuk failed test cases

## 🔧 Troubleshooting

### Common Issues:

1. **APK Not Found Error**
   ```bash
   # Verify APK path
   ls -la todolist/apk/to-do-list.apk
   ```

2. **Device Not Connected**
   ```bash
   # Check device connection
   adb devices
   adb kill-server && adb start-server
   ```

3. **Appium Server Connection**
   ```bash
   # Restart Appium server
   pkill -f appium
   appium
   ```

4. **Element Not Found**
   - Check element locators dalam `ToDoPage.java`
   - Verify app version compatibility
   - Increase wait times jika diperlukan

## 👨‍💻 Kontributor

**Nama**: Ganang Setyo Hadi

**NIM**: 2208107010052

**Mata Kuliah**: Kualitas Perangkat Lunak  

**Semester**: 6

## 📝 Lisensi

Project ini dibuat untuk keperluan akademis dalam rangka tugas UAS Kualitas Perangkat Lunak.

---

## 🎯 Kesimpulan

Project automation testing ini berhasil mengimplementasikan:
- ✅ 10 comprehensive test cases covering main app functionalities
- ✅ Stable and reliable test execution dengan success rate 100%
- ✅ Proper error handling dan reporting mechanisms
- ✅ Clean code architecture menggunakan design patterns
- ✅ Detailed documentation dan setup instructions

Automation testing ini membuktikan bahwa aplikasi ToDo List dapat berfungsi dengan baik sesuai dengan requirements dan user expectations. Framework yang dibangun dapat dengan mudah di-extend untuk menambah test cases tambahan seiring dengan perkembangan aplikasi.

---

**📧 Contact**: [ganangsetyohadi@gmail.com]  
**🔗 GitHub**: [https://github.com/notsuperganang/todo-list-automation-testing](https://github.com/notsuperganang/todo-list-automation-testing)