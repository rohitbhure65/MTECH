# Template Program - Code Explanation (Hinglish)

Yeh folder mein `template` concept ka use kiya gaya hai C++ mein, jisse hum ek hi function (`add`) ka use different data types (int, string, custom classes) ko add/combine karne ke liye kar sakte hain.

## 1. `combiner.h`
Yeh header file mein template function aur custom classes define ki gayi hain jinko combine kiya ja sakta hai.

```cpp
#ifndef COMBINER_H
#define COMBINER_H

#include <string>

using namespace std;

// Yeh ek generic template function hai jo do same type ki values ko add karta hai.
template <typename T>
T add(const T &a, const T &b){
    return a + b;
}

// File class jo file contents ko store karti hai aur combine (add) karne ka overload operator define karti hai.
class File{
public:
    string content;

    File(string c) : content(c) {}

    // '+' operator overloading taaki do File objects combine ho sakein nayi line ke sath.
    File operator+(const File &other) const
    {
        return File(this->content + "\n" + other.content);
    }
};

// Paragraph class jisme text store hota hai.
class Paragraph{
public:
    string text;

    Paragraph(string t) : text(t) {}

    // '+' operator overloading do paragraphs ko combine karne ke liye.
    Paragraph operator+(const Paragraph &other) const
    {
        return Paragraph(this->text + "\n" + other.text);
    }
};

// Image class jo image data (path/string) store karti hai.
class Image{
public:
    string imageData;

    Image(string data) : imageData(data) {}

    // '+' operator overloading do images ko combine karne ke liye.
    Image operator+(const Image &other) const
    {
        return Image(this->imageData + " [COMBINED WITH] " + other.imageData);
    }
};

#endif
```

## 2. `main.cpp`
Yeh main file `combiner.h` ko use karti hai integer, string, char, File, Paragraph, aur Image ko add ya combine karne ke liye.

```cpp
#include <iostream>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;

#include "combiner.h"

int main()
{
    // Integer addition
    int num1, num2;
    cout << "Enter first number: ";
    cin >> num1;
    cout << "Enter second number: ";
    cin >> num2;
    // add() function call ho raha hai generic template se
    cout << "Numbers sum: " << add(num1, num2) << "\n\n";

    cin.ignore();

    // String concatenation (judna)
    string str1, str2;
    cout << "Enter first string: ";
    getline(cin, str1);
    cout << "Enter second string: ";
    getline(cin, str2);
    cout << "String concatenation: " << add(str1, str2) << "\n\n";

    // Character addition (char ki ASCII value me int add hota hai)
    char c1, c2;
    int temp_c2;
    cout << "Enter a character: ";
    cin >> c1;
    cout << "Enter an integer to add to the character: ";
    cin >> temp_c2;
    c2 = temp_c2;
    // Char addition cast ho kar output de raha hai
    cout << "Char addition ('" << c1 << "' + " << temp_c2 << "): " << (char)add(c1, c2) << "\n\n";

    cin.ignore();

    // Files ko padh kar unko combine karna
    string fileName1, fileName2;
    cout << "Enter path/name for File 1: ";
    getline(cin, fileName1);
    cout << "Enter path/name for File 2: ";
    getline(cin, fileName2);

    // File 1 read karna
    ifstream f1(fileName1);
    string fileContent1;
    if (f1) {
        stringstream buffer;
        buffer << f1.rdbuf();
        fileContent1 = buffer.str();
    } else {
        cout << "Could not open File 1. Using empty content.\n";
    }

    // File 2 read karna
    ifstream f2(fileName2);
    string fileContent2;
    if (f2) {
        stringstream buffer;
        buffer << f2.rdbuf();
        fileContent2 = buffer.str();
    } else {
        cout << "Could not open File 2. Using empty content.\n";
    }

    // Custom File class ka use karke contents combine karna
    File file1(fileContent1);
    File file2(fileContent2);
    File combinedFile = add(file1, file2);
    cout << "Combined Files Content:\n"
         << combinedFile.content << "\n\n";

    // Paragraph addition
    string para1, para2;
    cout << "Enter text for Paragraph 1: ";
    getline(cin, para1);
    cout << "Enter text for Paragraph 2: ";
    getline(cin, para2);
    Paragraph p1(para1);
    Paragraph p2(para2);
    Paragraph combinedParagraph = add(p1, p2);
    cout << "Combined Paragraphs:\n"
         << combinedParagraph.text << "\n\n";

    // Image strings ka combination
    string imgPath1, imgPath2;
    cout << "Enter source path for Image 1: ";
    getline(cin, imgPath1);
    cout << "Enter source path for Image 2: ";
    getline(cin, imgPath2);
    Image image1(imgPath1);
    Image image2(imgPath2);
    Image combinedImage = add(image1, image2);
    cout << "Combined Image Sources:\n"
         << combinedImage.imageData << "\n";

    return 0;
}
```

## 3. `file1.txt` & `file2.txt`
Yeh dono dummy text files hain jo upar diye program mein file combine option ko test karne ke liye banai gayi hain.
