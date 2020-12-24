## Norbert Banyi's Personal Project: Patient Driven Pancreatic Cancer Data Tool

### Background
Recently the awareness of the importance of identifying hereditary pancreatic ductal adenocarcinoma (PDAC) has
increased the demand of genetic testing, which created challenges with the traditional genetic
service model because of long wait time and low genetic testing uptake. In a traditional genetic
testing streamline, PDAC patients and their kindred are referred to the Hereditary Cancer Program (HCP)
(or their local equivalent) for genetic counselling and genetic testing. In this model, only 60% 
of PDAC patient referrals in BC are fulfilled due to logistical reasons. This number is *even lower* 
for PDAC kindred. Therefore, many individuals who do not yet have pancreatic cancer but have a high 
risk to develop PDAC have limited access to medical geneticist with whom they can evaluate their 
risk and obtain genetic testing to inform their lifestyle habits. Wait times for these individuals
are in the order of years.

### Rationale
One of my career interests are improving cancer care. Having worked at BC Cancer in the department of Medical
Oncology, Pathology, and at the HCP over the last 3 years, I have recognized a variety of inefficiencies
that can be fixed by creating more effective programs. One prominent inefficiency is the initial
genetic consultation with individuals that have a high risk of PDAC and the current programs used to store
patient information.

A patient-driven PDAC risk assessment tool has the potential to replace the initial genetic consultation
thereby offering a more efficient risk assessment, reduce bottlenecking, and resource prioritization. 
This application would be used by patients to evaluate their own risk of developing PDAC.
The application can initially be used as a desktop application where individuals with high risk of PDAC
can come into the HCP and input their personal and familial risk factors. Eventually, it could be converted
into a web application where medical geneticists can email the link to the individuals. The application can 
also be used by medical geneticists to extract patient information either so that they can use it for 
assessing patient risk, prioritizing HCP resources, or doing research. 

### Application Description
In theory, this application can be improved to meet two main focuses: 

1. **Capturing patient risk factor information**

    Focus (1) will be addressed through the use of leading questions that the patients can respond to
    by either selecting from options, or by inputting words or numbers. The questions by the program
    will ask for important information that would have been asked for by a genetic counselor during consultation.
    The patient will be able to view their risk factor based on the information that they provided.

2. **Extracting and analyzing patient information**

    Focus (2) will be addressed through a variety of operations that individuals who would like to obtain the
    information of the patients can use including:
    - A function where a genetic counselor can create a pedigree for a patient of interest
    - A function where a genetic counselor can amend the information about the patient history and add notes to the
     patient file
    - A function that exports the patient information of chosen patients for analysis

How the user can interact with the application should depend on whether the user is a *patient* or *hospital staff*.

## User Stories That Have Been Implemented:
- As a user, I want to be able to create patient profile and potentially input them into the database
- As a user, I want to be able to add a patient's family history of cancer to a patient while creating the patient
- As a user, I want to be able to add multiple family cancer cases to a patient's family history of cancer
- As a user, I want to be able to add a patient's personal history with disease to the patient's profile
- As a user, I want to be able to view my database of patients by their first name, last name, and PHN
- As a user, I want to be able to select a patient and get the number of nth degree relatives who had any type of cancer
- As a user, I want to be able to be able to save my database
- As a user, I want to be able to load my database from file

#### UBC CPSC 210: Phase 4 - Task 2: 
- I have decided to implement the first option: to make a class robust. The class that I chose to make robust
is the PatientDatabase class. Previously, all methods but two were robust in this class. The two
methods that were not robust were the addPatientToDatabase(Patient p) and getPatient(int phn) methods. I now made
them throw a RepeatPhNException and a PatientNotInDatabaseException respectively. Now, no method in the PatientDatabase
class has a requires clause and all are robust.

#### UBC CPSC 210: Phase 4 - Task 3: 
 - First thing I would do to refactor would be to have an association between the StandardDatabaseWindow abstract class
 and the patient database class. All the subclasses that extend the StandardDatabaseWindow class would inherit
 the association.
 - I would also change the names of the window of my UI to be more consistent. Specifically, the names of the classes
 in the patient database screen series. 
 - There exists some redundancy in the code where I create the JTextFields for creating the patient in the patient
 database portion as well as when the provider searches for the patient. I would pull that to the superclass.
 - I would also put the JSON methods for loading and saving in the "StandardDatabaseWindow" class because if in the
 future, I wanted to be able to save and load in different windows, this would make it much more easy.
- There are also many methods that I would like to rename to be more descriptive. First place where I would start
to rename would be in the PatientDatabase class.
