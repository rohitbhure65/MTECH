# Corporate Programs - Code Explanation (Hinglish)

Yeh folder ek "Corporate Sponsored Degree & Training Registration System" implement karta hai. Isme alag-alag companies (jaise TCS, Wipro) apne employees ya candidates ke liye sponsored programs (MCA, M.Tech) run karti hain, jisme eligibility aur selection criteria apply hota hai.

**Sir ko impress karne ke liye main point:** Sir ko batana ki yeh ek Real-world problem ka **Object-Oriented Design (OOPs)** hai. Isme humne **Inheritance** (Base class se child class banana) aur **Smart Pointers (`shared_ptr`)** ka use kiya hai jisse memory leak na ho, aur pointers safely khud delete ho jayein.

## 1. `AcademicRecord.h`
Yeh class candidate ki academic details store karti hai. (Jaise marksheet)

```cpp
#pragma once
#include <string>

using namespace std;

class AcademicRecord {
    string degree; // Kounsi degree ki hai (e.g., BCA, BTECH)
    double cgpa;   // Kitne marks hain
    int graduationYear; // Kab pass kiya

public:
    // Constructor: Jab naya record banega, toh yeh sari details initialize karega
    AcademicRecord(string degree, double cgpa, int graduationYear)
        : degree(degree), cgpa(cgpa), graduationYear(graduationYear) {}

    // Getters: Bahar se in values ko read karne ke functions
    double getCgpa() const { return cgpa; }
    string getDegree() const { return degree; }
    int getGraduationYear() const { return graduationYear; }
};
```

## 2. `Candidate.h`
Candidate class ek applicant ki profile maintain karti hai. Ek bachha jab apply karta hai toh wo apni detail aur apni marksheet (AcademicRecord) le kar aata hai.

```cpp
#pragma once
#include <string>
#include "AcademicRecord.h"

using namespace std;

class Candidate {
    string name;
    string email;
    AcademicRecord record; // Candidate ka academic data store ho raha hai (Composition concept)

public:
    // Constructor
    Candidate(string name, string email, AcademicRecord record)
        : name(name), email(email), record(record) {}

    string getName() const { return name; }
    // Const reference se record return kar raha hai taaki data copy na ho (performance ke liye)
    const AcademicRecord& getRecord() const { return record; }
    string getEmail() const { return email; }
};
```

## 3. `CorporateProgram.h`
Yeh class corporate program ki details define karti hai, jaise TCS Ignite ya Wipro WILP.

```cpp
#pragma once
#include <string>

using namespace std;

class CorporateProgram {
    string programName; // Jaise: "WILP M.Tech"
    string category;    // Jaise: "MTECH"
    int durationMonths; // Program kitne mahine ka hai

public:
    CorporateProgram(string programName, string category, int durationMonths)
        : programName(programName), category(category), durationMonths(durationMonths) {}

    string getProgramName() const { return programName; }
    string getCategory() const { return category; }
    int getDurationMonths() const { return durationMonths; }
};
```

## 4. `Company.h`
Company class (e.g., TCS) programs ki ek list maintain karti hai jo woh offer kar rahi hai.

```cpp
#pragma once
#include <string>
#include <vector>
#include <memory>
#include "CorporateProgram.h"

using namespace std;

class Company {
    string name;
    // 'vector' ek dynamic array hai.
    // 'shared_ptr' ka use isliye kiya hai taaki memory khud manage ho. Agar program ka use khatam ho gaya toh memory delete ho jayegi.
    vector<shared_ptr<CorporateProgram>> offeredPrograms;

public:
    Company(string name) : name(name) {}

    // Ek naya program company me add karne ke liye
    void addProgram(shared_ptr<CorporateProgram> program) {
        offeredPrograms.push_back(program);
    }

    string getName() const { return name; }
};
```

## 5. `SelectionCriteria.h`
Yeh ek **abstract base class (interface)** hai. Yeh bahut important hai. Isme sirf rules ke naam hain, rules actually kaise kaam karenge yeh dusri class batayegi.

```cpp
#pragma once
#include "Candidate.h"

class SelectionCriteria {
public:
    // '= 0' ka matlab yeh "Pure Virtual Function" hai. 
    // Jo bhi is class ko inherit karega, use yeh function likhne padenge.
    virtual bool isEligible(const Candidate& candidate) const = 0; // Eligible hai ya nahi
    virtual double calculateScore(const Candidate& candidate) const = 0; // Score kitna hai
    virtual ~SelectionCriteria() = default; // Destructor
};
```

## 6. `StandardSelectionCriteria.h`
Yeh class purani class (`SelectionCriteria`) ko inherit karti hai aur asal logic likhti hai.

```cpp
#pragma once
#include <vector>
#include <string>
#include <algorithm>
#include "SelectionCriteria.h"

using namespace std;

// 'public SelectionCriteria' matlab isne us interface ko inherit kiya
class StandardSelectionCriteria : public SelectionCriteria {
    double minCgpa; // Minimum required CGPA
    vector<string> allowedDegrees; // Allowed degrees ki list (jaise BCA, BSC sirf)

public:
    StandardSelectionCriteria(double minCgpa, const vector<string>& allowedDegrees)
        : minCgpa(minCgpa), allowedDegrees(allowedDegrees) {}

    // isEligible ko override kiya (apna rule likha)
    bool isEligible(const Candidate& candidate) const override {
        const AcademicRecord& rec = candidate.getRecord();
        
        // Find function check karega ki bacche ki degree allowedDegrees me hai ya nahi
        bool degreeAllowed = find(allowedDegrees.begin(), allowedDegrees.end(), rec.getDegree()) != allowedDegrees.end();
        
        // Agar marks limit se zyada hain AND degree allowed hai, tabhi TRUE hoga.
        return rec.getCgpa() >= minCgpa && degreeAllowed;
    }

    // Score calculate karne ka rule (yahan simply CGPA return kar rahe hain)
    double calculateScore(const Candidate& candidate) const override {
        return candidate.getRecord().getCgpa();
    }
};
```

## 7. `RecruitmentProcess.h`
Yeh poore process ka engine hai. Forms receive karta hai aur final list nikalta hai.

```cpp
#pragma once
#include <iostream>
#include <vector>
#include <memory>
#include <algorithm>
#include "Company.h"
#include "CorporateProgram.h"
#include "SelectionCriteria.h"
#include "Candidate.h"

using namespace std;

class RecruitmentProcess {
    shared_ptr<Company> company;
    shared_ptr<CorporateProgram> program;
    int batchYear;
    int intakeCapacity; // Sirf kitne bacche lene hain (Seats)
    shared_ptr<SelectionCriteria> criteria; // Selection ka rule
    vector<Candidate*> applicants; // Jinhone apply kiya
    vector<Candidate*> selectedCandidates; // Jo pass hue

public:
    // Process shuru karne se pehle sari zaruri cheezein bataani padengi
    RecruitmentProcess(shared_ptr<Company> company, shared_ptr<CorporateProgram> program, int batchYear, int intakeCapacity, shared_ptr<SelectionCriteria> criteria)
        : company(company), program(program), batchYear(batchYear), intakeCapacity(intakeCapacity), criteria(criteria) {}

    // Bachha jab form bharega
    void apply(Candidate& candidate) {
        applicants.push_back(&candidate);
    }

    // Pura selection chalane ka function
    void processSelection() {
        vector<Candidate*> eligible;
        
        // 1. Eligibility check karo
        for (Candidate* c : applicants) {
            // Hamara banaya hua rule isEligible check hoga
            if (criteria->isEligible(*c)) {
                eligible.push_back(c);
            }
        }

        // 2. Sorting karo (Jiske CGPA sabse jyada, wo sabse upar)
        // Lamda function ka use karke descending order me sort kar rahe hain
        sort(eligible.begin(), eligible.end(), [this](Candidate* c1, Candidate* c2) {
            return criteria->calculateScore(*c1) > criteria->calculateScore(*c2);
        });

        cout << "--- Selection List for " << company->getName() << " " << program->getProgramName() << " (" << batchYear << ") ---" << endl;
        int count = 0;
        
        // 3. Intake capacity (seats) ke hisab se baccho ko finally select karo
        for (Candidate* c : eligible) {
            if (count < intakeCapacity) {
                selectedCandidates.push_back(c);
                cout << c->getName() << " - Selected with CGPA: " << c->getRecord().getCgpa() << endl;
                count++; // Ek seat bhar gayi
            }
        }
        
        if (selectedCandidates.empty()) {
            cout << "No candidates met the eligibility criteria or applied." << endl;
        }
        cout << "-------------------------------------------------" << endl;
    }
};
```

## 8. `main.cpp`
Main program jahan saari classes jud ke run hoti hain.

```cpp
#include <iostream>
#include <memory>
#include <vector>
#include <string>
#include "AcademicRecord.h"
#include "Candidate.h"
#include "SelectionCriteria.h"
#include "StandardSelectionCriteria.h"
#include "CorporateProgram.h"
#include "Company.h"
#include "RecruitmentProcess.h"

using namespace std;

int main() {
    cout << "=== Corporate Sponsored Degree & Training Registration System ===\n" << endl;

    // 1. Companies create ki (TCS aur Wipro)
    auto tcs = make_shared<Company>("TCS");
    auto wipro = make_shared<Company>("Wipro");

    // 2. Corporate programs banaye
    auto tcsIgnite = make_shared<CorporateProgram>("Ignite MCA", "MCA", 24);
    auto wiproWilp = make_shared<CorporateProgram>("WILP M.Tech", "MTECH", 48);

    // Company ke andar program add kiya
    tcs->addProgram(tcsIgnite);
    wipro->addProgram(wiproWilp);

    // 3. Bachhe aur unke records banaye
    AcademicRecord rec1("BCA", 8.5, 2024);
    Candidate c1("Ramesh", "ramesh@email.com", rec1); // BCA wala

    AcademicRecord rec2("BSC", 9.2, 2024);
    Candidate c2("Suresh", "suresh@email.com", rec2); // BSC wala

    AcademicRecord rec3("BTECH", 7.5, 2024);
    Candidate c3("Anita", "anita@email.com", rec3); // BTECH wali

    // 4. Criteria set kiya 
    // Wipro ko BCA, BSC wale minimum 6.0 CGPA chahiye
    auto wilpCriteria = make_shared<StandardSelectionCriteria>(6.0, vector<string>{"BCA", "BSC"});
    // TCS ko BCA, BSC wale minimum 7.0 CGPA chahiye
    auto igniteCriteria = make_shared<StandardSelectionCriteria>(7.0, vector<string>{"BCA", "BSC"});

    // 5. Recruitment processes initialize kiye
    // Wipro process me sirf 1 seat hai (intakeCapacity = 1)
    RecruitmentProcess wiproProcess(wipro, wiproWilp, 2024, 1, wilpCriteria);
    // TCS process me 2 seat hain (intakeCapacity = 2)
    RecruitmentProcess tcsProcess(tcs, tcsIgnite, 2024, 2, igniteCriteria);

    // 6. Bachho ne apply kiya
    wiproProcess.apply(c1);
    wiproProcess.apply(c2);
    wiproProcess.apply(c3); // Anita reject hogi kyonki uska BTECH hai jo list me allow nahi hai

    tcsProcess.apply(c1);
    tcsProcess.apply(c2);
    
    // 7. Process chalana aur list nikalna
    wiproProcess.processSelection();
    // Suresh aage hoga list me kyonki uske 9.2 CGPA hai (Seat 1 hi hai toh sirf Suresh select hoga)
    cout << endl;
    
    tcsProcess.processSelection();
    // Yahan 2 seats hain toh Ramesh aur Suresh dono select honge

    return 0;
}
```
