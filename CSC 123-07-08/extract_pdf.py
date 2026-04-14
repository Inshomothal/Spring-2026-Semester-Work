#!/usr/bin/env python3
"""Extract text from PDF file."""

import sys
import os

pdf_path = r"C:\Users\Trevon Collins\OneDrive\Documents\Education\2026 Spring Semester\CSC 123-07-08\Grades for Trevon Collins_ CSC-123-07-Intro To Csc And Prog II-23125.pdf"

# Try pdfplumber first
try:
    import pdfplumber
    print("=" * 80)
    print("USING PDFPLUMBER")
    print("=" * 80)
    with pdfplumber.open(pdf_path) as pdf:
        print(f"Total pages: {len(pdf.pages)}\n")
        for page_num, page in enumerate(pdf.pages, 1):
            print(f"\n{'=' * 80}")
            print(f"PAGE {page_num}")
            print('=' * 80)
            text = page.extract_text()
            if text:
                print(text)
            else:
                print("[No text found on this page]")
except ImportError:
    print("pdfplumber not installed, trying PyMuPDF...")
    try:
        import fitz
        print("=" * 80)
        print("USING PyMuPDF (fitz)")
        print("=" * 80)
        doc = fitz.open(pdf_path)
        print(f"Total pages: {len(doc)}\n")
        for page_num in range(len(doc)):
            print(f"\n{'=' * 80}")
            print(f"PAGE {page_num + 1}")
            print('=' * 80)
            page = doc[page_num]
            text = page.get_text()
            if text:
                print(text)
            else:
                print("[No text found on this page]")
        doc.close()
    except ImportError:
        print("PyMuPDF not installed, trying pypdf...")
        try:
            from pypdf import PdfReader
            print("=" * 80)
            print("USING pypdf")
            print("=" * 80)
            reader = PdfReader(pdf_path)
            print(f"Total pages: {len(reader.pages)}\n")
            for page_num, page in enumerate(reader.pages, 1):
                print(f"\n{'=' * 80}")
                print(f"PAGE {page_num}")
                print('=' * 80)
                text = page.extract_text()
                if text:
                    print(text)
                else:
                    print("[No text found on this page]")
        except ImportError:
            print("ERROR: None of the PDF libraries are installed!")
            print("Available libraries: pdfplumber, PyMuPDF (fitz), pypdf")
            sys.exit(1)
except Exception as e:
    print(f"Error: {e}")
    import traceback
    traceback.print_exc()
    sys.exit(1)
