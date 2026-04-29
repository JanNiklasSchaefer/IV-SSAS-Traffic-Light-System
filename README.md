# Secure Traffic Light System

A Kubernetes-native traffic light management system built with Quarkus and Docker,
developed as part of a university security engineering project. 

## Overview

This project focuses on implementing a **secure distributed system** — covering
authentication, authorization, access control, and endpoint hardening across a
microservice architecture deployed on Kubernetes (kind cluster).

A full description of the system's functionality can be found in [`Task5/README.md`](./Task5/README.md).

## Security Highlights

- **Authentication** — OAuth 2.0 / OIDC via Keycloak
- **Authorization** — Role-Based Access Control (RBAC) via Keycloak
- **Secure Communication** — mTLS for all internal cluster communication, TLS/mTLS for external clients
- **Secure Endpoints** — Input validation and protection against common vulnerabilities

## Tech Stack

- Quarkus
- Docker
- Kubernetes (kind)

## Note on Deployment

This repository requires a running kind cluster to function. The cluster configuration
is not included due to licensing constraints. This repo is intended as a **portfolio
reference**, not a standalone deployable project.

---

MIT License — © 2026 Jan Niklas Schäfer, Krzysztof Lagowski
