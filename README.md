# AWS Resource Governance Platform – Refactored Plan

## 1. Abstract

This project aims to provide **governance, visibility, and drift detection** for AWS cloud environments. It continuously discovers AWS resources, captures their state using snapshots, and compares them against an **intended architecture** defined by users through diagrams and mandatory tagging.

The system is designed to be:

* Tag-driven (deterministic and enterprise-friendly)
* Snapshot-based (auditable and time-aware)
* LLM-assisted (diagram understanding, not authoritative discovery)

---

## 2. Core Principles

1. **Tags are the source of truth**
   Users must apply mandatory tags (e.g. `project_id`, `architecture`) to AWS resources. All comparisons and governance checks rely on these tags.

2. **AWS discovery beats inference**
   Actual resources always come from AWS (Module 1). LLMs are only used to interpret *intent*, never reality.

3. **Snapshots over real-time**
   All comparisons are snapshot-scoped, enabling auditing, rollback, and historical analysis.

4. **Incremental governance**
   The system delivers value early (tag compliance) and evolves toward full drift detection.

---

## 3. Module Overview

### Module 1 – AWS Resource Discovery & Snapshotting

**Responsibility**: Discover and store the actual state of AWS resources.

#### Key Capabilities

* Enable/disable supported AWS resource types
* Discover AWS resources using AWS Resource Explorer
* Persist resource details, properties, and tags
* Capture point-in-time snapshots
* Compare snapshots for resource-level changes

#### Key APIs

* `GET /api/supported-resources`
* `POST /api/supported-resources`
* `POST /api/resources/snapshots`
* `POST /api/resources/snapshots/compare`

#### Data Model (Existing)

* `SNAPSHOT`
* `AWS_RESOURCE_DETAILS`
* `AWS_RESOURCE_DETAILS_PROPERTY`
* `AWS_SUPPORTED_RESOURCE_TYPE`

Tags are stored in `AWS_RESOURCE_DETAILS_PROPERTY` with:

* `NAME = 'tags'`
* `DATA = JSON array of { Key, Value }`

---

### Module 2 – Architecture Intent, Compliance & Drift Detection

**Responsibility**: Capture intended architecture and compare it with actual AWS state.

Module 2 builds entirely on top of Module 1 snapshots.

---

## 4. Module 2 – Implementation Phases

### Phase 1 – Tag-Based Foundation (Completed / In Progress)

**Objective**: Establish reliable tag parsing and compliance checks.

#### Phase 1.1 – Tag Parsing & Normalization ✅

* Parse tags JSON from `AWS_RESOURCE_DETAILS_PROPERTY.DATA`
* Normalize keys and values (trim, lowercase)
* Fail-safe against malformed data

#### Phase 1.2 – Snapshot-Scoped Resource Fetch ✅

* Fetch resources by `snapshot_id`
* Include raw tags JSON
* Parse tags in Java (not SQL)

#### Phase 1.3 – Tag Compliance (TAG_MISSING) ✅

* Detect resources missing mandatory tags:

  * `project_id`
  * `architecture`
* Produce actionable governance violations

**Outcome**: First enterprise-grade governance signal.

---

### Phase 2 – Architecture Diagram Metadata & Ingestion

**Objective**: Anchor intended architecture to snapshots and tags.

#### Phase 2.1 – Architecture Diagram Metadata ✅

* Store diagram metadata
* Link diagram to:

  * `project_id`
  * `architecture`
  * baseline `snapshot_id`
* Track lifecycle status (CREATED, PROCESSING, COMPLETED, FAILED)

#### Phase 2.2 – Diagram Upload (Next)

* Upload architecture diagram image
* Store image in S3
* Persist `IMAGE_S3_URL`
* Update status to `IMAGE_UPLOADED`

---

### Phase 3 – LLM-Based Architecture Extraction

**Objective**: Extract intended resources from architecture diagrams.

* Send diagram image to Vision-capable LLM
* Extract structured JSON:

  * AWS services
  * resource types
  * regions
  * expected tags
  * high-level configuration
* Validate against strict schema
* Persist intended architecture resources

LLM output is **advisory**, never authoritative.

---

### Phase 4 – Drift Detection Engine

**Objective**: Compare intended vs actual state.

#### Drift Types

* `MISSING` – Intended resource not found in AWS
* `EXTRA` – AWS resource not present in diagram
* `CONFIG_MISMATCH` – Property differences
* `TAG_MISSING` – Governance violation

#### Matching Strategy

* `service + resource_type + region`
* Filtered strictly by required tags

Results are persisted for auditing.

---

### Phase 5 – APIs & Automation

* `GET /api/architecture-diagrams/{id}`
* `GET /api/architecture-diagrams/{id}/drift`
* Scheduled drift checks (daily / weekly)

---

### Phase 6 – Future Enhancements

* Tag value validation (allowed values)
* Environment isolation (prod / staging)
* Cost attribution by `project_id`
* Notifications (Slack / Email)
* AWS Config & EventBridge integration

---

## 5. Summary

This design:

* Avoids schema rewrites
* Leverages AWS as the source of truth
* Uses LLMs safely and deterministically
* Scales from compliance to full drift governance

The project delivers **incremental value at every phase** while remaining enterprise-ready.
