# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Unittest validatie project dat valideert of de Swagger en de datavoorbeelden matchen en valideren.

### Changed
- @id is enkel verplicht bij objecten of codelijsten die een PURI hebben, toegepast op Swagger en datavoorbeelden.
- Kardinaliteiten aangepast van Versie.creatiedatum en Versie.bron voor filtering.

### Fixed
- Titel Vocabularium en Implementatiemodel volgen nu de syntaxregels.
- Publicatiedatum
- Stakeholder lijst is aangepast volgens het Implementatiemodel ipv Applicatieprofiel.
- test.data.vlaanderen.be URIs vervangen door implementatie.data.vlaanderen.be URIs vooraleer naar productie te gaan.

## [0.0.2] - 2026-04-21

### Fixed
- Configuratienaam zodat Content-Negotiation actief is.

## [0.0.1] - 2026-04-20

### Added
- Initiële release
