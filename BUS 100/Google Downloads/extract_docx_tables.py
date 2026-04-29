from docx import Document
import os
import json

def extract_tables_with_todos(docx_path):
    doc = Document(docx_path)
    tables_info = []
    for table in doc.tables:
        table_data = []
        for row in table.rows:
            row_data = [cell.text.strip() for cell in row.cells]
            table_data.append(row_data)
        # Check for to-do like keywords in any cell
        if any(any('to-do' in cell.lower() or 'task' in cell.lower() or 'responsibilit' in cell.lower() or 'duty' in cell.lower() for cell in row) for row in table_data):
            tables_info.append(table_data)
    return tables_info

files = [f for f in os.listdir('.') if f.endswith('.docx')]
results = {}
for file in files:
    tables = extract_tables_with_todos(file)
    if tables:
        results[file] = tables
print(json.dumps(results, indent=2))
