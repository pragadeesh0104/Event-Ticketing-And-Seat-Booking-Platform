# Project Diagrams Documentation

This directory contains the editable Mermaid source files for all core system diagrams:

1. `architecture.mmd` - High-level Full Stack System Architecture Diagram
2. `er_diagram.mmd` - Complete Entity-Relationship (ER) Database Schema Diagram
3. `uml_class_diagram.mmd` - UML Class Diagram for domain entities and services

## How to Render Diagrams
- **VS Code**: Install the extension `Mermaid Preview` or `Markdown Preview Mermaid Support` to render directly inside VS Code.
- **Online**: Copy and paste the contents into [Mermaid Live Editor](https://mermaid.live).
- **CLI**: Convert to PNG/SVG using `@mermaid-js/mermaid-cli`:
  ```bash
  npx @mermaid-js/mermaid-cli -i architecture.mmd -o architecture.png
  npx @mermaid-js/mermaid-cli -i er_diagram.mmd -o er_diagram.png
  npx @mermaid-js/mermaid-cli -i uml_class_diagram.mmd -o uml_class_diagram.png
  ```
