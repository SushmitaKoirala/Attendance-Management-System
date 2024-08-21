
-- Create the Students table
CREATE TABLE Students (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DateOfBirth DATE,
    ContactNumber VARCHAR(15),
    Email VARCHAR(100),
    Address TEXT
);

-- Create the Teachers table
CREATE TABLE Teachers (
    TeacherID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    ContactNumber VARCHAR(15),
    Email VARCHAR(100),
    Address TEXT
);

-- Create the Classes table
CREATE TABLE Classes (
    ClassID INT AUTO_INCREMENT PRIMARY KEY,
    ClassName VARCHAR(50) NOT NULL,
    ClassDescription TEXT
);

-- Create the Subjects table
CREATE TABLE Subjects (
    SubjectID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectName VARCHAR(100) NOT NULL,
    SubjectCode VARCHAR(20) NOT NULL
);

-- Create the StudentClasses relationship table
CREATE TABLE StudentClasses (
    StudentClassID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT,
    ClassID INT,
    EnrollmentDate DATE,
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

-- Create the TeacherClasses relationship table
CREATE TABLE TeacherClasses (
    TeacherClassID INT AUTO_INCREMENT PRIMARY KEY,
    TeacherID INT,
    ClassID INT,
    FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

-- Create the SubjectClasses relationship table
CREATE TABLE SubjectClasses (
    SubjectClassID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectID INT,
    ClassID INT,
    FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

-- Create the AttendanceRecords table
CREATE TABLE AttendanceRecords (
    AttendanceID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT,
    ClassID INT,
    SubjectID INT,
    TeacherID INT,
    AttendanceDate DATE,
    Status ENUM('Present', 'Absent', 'Late') NOT NULL,
    Comments TEXT,
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID),
    FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID),
    FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID)
);

-- Create indexes for frequently queried columns (if needed)
CREATE INDEX idx_StudentID ON AttendanceRecords (StudentID);
CREATE INDEX idx_ClassID ON AttendanceRecords (ClassID);
CREATE INDEX idx_SubjectID ON AttendanceRecords (SubjectID);
CREATE INDEX idx_TeacherID ON AttendanceRecords (TeacherID);

-- Create the 'students' table
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    major VARCHAR(50)
);

-- Create the 'courses' table
CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    course_code VARCHAR(20),
    course_name VARCHAR(100),
    credits INT
);

-- Create the 'semesters' table
CREATE TABLE semesters (
    semester_id INT PRIMARY KEY,
    semester_number INT,
    start_date DATE,
    end_date DATE
);

-- Create a junction table to represent student-course registrations
CREATE TABLE student_course_registration (
    registration_id INT PRIMARY KEY,
    student_id INT,
    course_id INT,
    semester_id INT,
    grade VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (semester_id) REFERENCES semesters(semester_id)
);

-- Insert data into the 'students' table
INSERT INTO students (student_id, first_name, last_name, date_of_birth, major) VALUES
    (1, 'John', 'Doe', '1990-01-15', 'Computer Science'),
    (2, 'Jane', 'Smith', '1991-05-20', 'Engineering'),
    (3, 'Alice', 'Johnson', '1992-08-10', 'Mathematics');

-- Insert data into the 'courses' table
INSERT INTO courses (course_id, course_code, course_name, credits) VALUES
    (101, 'CS101', 'Introduction to Programming', 3),
    (201, 'ENG201', 'Engineering Mechanics', 4),
    (301, 'MATH301', 'Calculus III', 4);

-- Insert data into the 'semesters' table (assuming 8 semesters)
INSERT INTO semesters (semester_id, semester_number, start_date, end_date) VALUES
    (1, 1, '2020-08-25', '2020-12-18'),
    (2, 2, '2021-01-11', '2021-05-07'),
    (3, 3, '2021-08-30', '2021-12-17'),
    (4, 4, '2022-01-10', '2022-05-06'),
    (5, 5, '2022-08-29', '2022-12-16'),
    (6, 6, '2023-01-09', '2023-05-05'),
    (7, 7, '2023-08-28', '2023-12-15'),
    (8, 8, '2024-01-08', '2024-05-03');

-- Insert data into the 'student_course_registration' table (sample registrations)
INSERT INTO student_course_registration (student_id, course_id, semester_id, grade) VALUES
    (1, 101, 1, 'A'),
    (1, 201, 1, 'B+'),
    (2, 301, 1, 'A'),
    (2, 101, 2, 'A-'),
    (3, 201, 2, 'B'),
    (3, 301, 2, 'A');

