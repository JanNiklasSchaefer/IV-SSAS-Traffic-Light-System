## IV Software Security for Autonomous

## Systems

### Dr. Karsten Bsufka

### karsten.bsufka@tu-berlin.de

### WiSe 2025/

## Contents

**List of Figures vii**

## List of Tables

```
2.1. Key size comparison.............................. 18
```

```
4.1. Types of requirements............................. 47
```

```
xi
```

## Listings

- I. Lecture Notes List of Tables xi
- 1. Introduction
  - 1.1. Autonomous systems
    - 1.1.1. Self-adaptive systems
    - 1.1.2. Distributed systems
    - 1.1.3. Microservices
    - 1.1.4. Securely developing microservices
  - 1.2. Security and the software development life-cycle
  - 1.3. IT security: Basic goals and concepts
  - 1.4. Summary
- 2. Cryptography
  - 2.1. Introduction
  - 2.2. Integrity
  - 2.3. Confidentiality
    - 2.3.1. Symmetric ciphers
    - 2.3.2. Asymmetric Ciphers
  - 2.4. Digital signatures
  - 2.5. Public key infrastructure
    - 2.5.1. Certification Authority
    - 2.5.2. X.509 certificate and CRL
    - 2.5.3. X.509 certificate validation
  - 2.6. Random Numbers
  - 2.7. Kubernetes, Quarkus, and PKI for TLS
  - 2.8. Create a TLS certificate for a microservice
  - 2.9. Conclusion
- 3. Kubernetes and Microservices
  - 3.1. Microservices
    - 3.1.1. What are microservices?
    - 3.1.2. Microservices and security
  - 3.2. Overview of used technologies
  - 3.3. Kubernetes
    - 3.3.1. Kubernetes components
    - 3.3.2. Kubernetes nodes
    - 3.3.3. Ingress
  - 3.4. Quarkus
    - 3.4.1. Service discovery
    - 3.4.2. Secret storage
    - 3.4.3. Access control
- 4. Software requirements
  - 4.1. Introduction to requirements engineering
  - 4.2. Security requirements
    - 4.2.1. Sources of information
    - 4.2.2. Types of security requirements
    - 4.2.3. Threat Modeling
    - 4.2.4. Misuse cases
    - 4.2.5. Attack trees
    - 4.2.6. Combining attack trees and misuse cases
  - 4.3. Summary
- 5. Software architectures and software design
  - 5.1. UML
  - 5.2. What we want to achieve during design
    - 5.2.1. Software Design Principles
    - 5.2.2. Design Pattern
  - 5.3. Summary
- 6. Security Patterns
  - 6.1. Introduction
  - 6.2. Pattern-driven Security Design
    - 6.2.1. Overview
    - 6.2.2. Security Requirements
    - 6.2.3. Security Architecture
    - 6.2.4. Security Design
    - 6.2.5. Implementation
  - 6.3. Security Pattern
    - 6.3.1. Security Pattern Template
    - 6.3.2. Software Security Pattern
  - 6.4. Introduction
  - 6.5. Security Choke Points
    - 6.5.1. Secure Base Action
    - 6.5.2. Secure Service Façade
    - 6.5.3. XACML Policy Enforcement
    - 6.5.4. Message Interceptor Gateway
    - 6.5.5. Message Inspector
    - 6.5.6. API Gateway
  - 6.6. Authentication & Authorization
    - 6.6.1. Access Token
    - 6.6.2. Authentication Enforcer
    - 6.6.3. Authorization Enforcer
  - 6.7. Logging and Auditing
    - 6.7.1. Secure Logger
    - 6.7.2. Audit Interceptor
  - 6.8. Summary
    - 6.8.1. Pattern Overview
    - 6.8.2. Using Software Security Patterns
- 7. System Security Patterns
  - 7.1. Introduction
  - 7.2. Access Control Model Patterns
    - 7.2.1. Role-based Access Control
  - 7.3. Firewalls
    - 7.3.1. Packet Filter Firewall
    - 7.3.2. Stateful Firewall
  - 7.4. Secure Internet Applications
    - 7.4.1. Demilitarized Zone
    - 7.4.2. Protection Reverse Proxy
    - 7.4.3. Integration Reverse Proxy
    - 7.4.4. Summary
- 8. Technology Selection
  - 8.1. Introduction
  - 8.2. Fundamental Decisions
  - 8.3. Making Choices
    - 8.3.1. Programming Languages
    - 8.3.2. Service/communication technologies
    - 8.3.3. Other
  - 8.4. Additional Choices
    - 8.4.1. Java
  - 8.5. Summary
- 9. Development
  - 9.1. Introduction
  - 9.2. Guiding principles & deadly sins
  - 9.3. Coding and security
    - 9.3.1. Code review
- 10.Testing
  - 10.1. Introduction
  - 10.2. Security Testing
  - 10.3. Test Documentation
  - 10.4. Penetration Testing
- 11.Operation
  - 11.1. Introduction
  - 11.2. Operations
- 12.Common Criteria
  - 12.1. Introduction
- 13.Self-Adaptive Systems
  - 13.1. Introduction
    - 13.1.1. Definition
    - 13.1.2. Requirements
    - 13.1.3. Example
    - 13.1.4. Reference Architecture
  - 13.2. Taxonomy
    - 13.2.1. Taxonomy - “What”
  - 13.3. Introduction
    - 13.3.1. Definition
  - 13.4. Conclusion and Outlook
- II. Lecture Example & Homework Assignments
- 14.Lecture & tutorial example
  - 14.1. Why Self-adaptive Cybersecurity Monitoring and Microservices?
    - 14.1.1. Cybersecurity Monitoring and Security Requirements
  - 14.2. System Overview
- 15.Homework Scenario: Microservice Architecture for Traffic Light Management
  - 15.1. Scenario introduction
    - 15.1.1. A short time ago in a town far, far away
    - 15.1.2. Smart Autonomous Main Street - Project vision
    - 15.1.3. Your mission
    - 15.1.4. Technology constraints & general homework requirements
    - 15.1.5. Aspects that will impact the grading
    - 15.1.6. Aspects that will not impact the grading
  - 15.2. Homework assignment overview
- Appendices
- A. Quick references
  - A.1. Kubernetes
  - A.2. Quarkus
- B. Installing cert-manager, Kyverno and trust-manager
- Acronyms
- Glossary
- Index
- Bibliography
- 1.1. Monolith vs. Microservice List of Figures
- 1.2. Waterfall model
- 1.3. Security terms
- 1.4. Security & SDLC
- 1.5. Security, SDLC & IV SSAS
- 2.1. Only Message Authentication (Integrity
- 2.2. Message Authentication (Integrity) & Confidentiality
- 2.3. Model for Network Security
- 2.4. Cryptosystem
- 2.5. Symmetric Cipher Model
- 2.6. The ECB Pinguin
- 2.7. Encryption
- 2.8. Authentication
- 2.9. Digital Signatures Source: [44]
- 2.10. Certificate and Certificate Revocation List (CRL)
- 2.11. cert-manager
- 2.12. PKI and microservice certificates.
- 3.1. Monolith vs. Microservice
- 3.2. Kubernetes, microservices & Quarkus
- 3.3. Docker workflow
- 3.4. Container vs. virtual machine
- 3.5. Kubernetes overview
- 3.6. Deploy to Kubernetes
- 3.7. Kubernetes components
- 3.8. Stacked etcd topology
- 3.9. Ingress
- 4.4. Requirements and relationships
- 4.5. Security requirements and influences
- 4.6. Simple misuse case example
- 4.7. Detailed misuse case example
- 4.8. Security and misuse case example
- 4.9. Difference between misuse and security use case
- 4.10. Attack tree
- 4.11. Attack tree/Misuse case relationship
- 4.12. Attack tree/Attack pattern relationship
- 5.3. High-level system model COBRA-5G
- 5.4. System context diagram: COBRA-5G - Monitoring & attack simulation
- 5.5. Container diagram: COBRA-5G microservices for monitoring
- 5.6. Component diagram: COBRA-5G Osquery mircroservice
- 5.7. Overview of UML diagram types
- 5.8. Model View Controller (MVC) pattern
- 5.9. Design pattern catalogue
- 5.10. Microservice pattern
- 6.1. Pattern-driven Security Design
- 6.2. Software Security Pattern Overview
- 6.3. Web Tier
- 6.4. Business Logic Tier
- 6.5. Web Services Tier
- 6.6. Identity Tier
- 6.7. Software Security Pattern Overview
- 6.8. Secure Base Action
- 6.9. Secure Service Façade
- 6.10. XACML
- 6.11. Message Interceptor Gateway
- 6.12. Sequence diagram: Message Interceptor Gateway
- 6.13. Message Inspector - Alternative
- 6.14. Message Inspector - Alternative
- 6.15. API Gateway
- 6.16. Authentication Enforcer
- 6.17. Authentication Enforcer (JAAS)
- 6.18. Authorization Enforcer
- 6.19. Secure Logger
- 6.20. Secure Logger - Remote logging
- 6.21. Audit Interceptor
- 6.22. Software Security Pattern - Web Tier
- 6.23. Software Security Pattern - Business Logic Tier
- 6.24. Software Security Pattern - Web Services Tier
- 7.1. Useless security
- 7.2. Access control patterns
- 7.3. RBAC - Simple overview
- 7.4. Core RBAC
- 7.5. Hierarchical RBAC
- 7.6. Static Separation of Duty
- 7.7. Dynamic Separation of Duty
- 7.8. Firewalls
- 7.9. Packet Filter Firewall
- 7.10. Sequence diagram for a packet filter firewall.
- 7.11. Firewalls and TCP connections
- 7.12. Stateful Firewall
- 7.13. Stateful Firewall - Sequence diagram
- 7.14. Secure Internet Application Patterns
- 7.15. Example network with a DMZ
- 7.16. Protection Reverse Proxy
- 7.17. Integration Reverse Proxy
- 9.1. SpotBugs
- 9.2. SonarQube
- 10.1. Testing
- 10.2. V-Model
- 10.4. Test plan
- 10.5. Test environment description
- 10.6. Test specification description
- 10.7. Test specification description
- 10.8. Test Result Overview
- 11.1. Where can something go wrong?
- 11.2. Layers of security and operation practices
- 11.3. Case 2: Due diligence review from hell - Network Part I
- 11.4. Case 2: Due diligence review from hell - Network Part II
- 11.5. Metric Data Collection
- 11.6. Anomaly (Fault) Detection in Metric Data
- 12.1. Common Criteria History
- 12.2. Roles and Evaluation Workflow
- 12.3. Molbile agents and multiagent systems
- 12.4. Molbile agents and multiagent systems - Threats
- 12.5. The ToE for the JIAC common criteria evaluation.
- 12.6. Security Target Example - SSL
- 12.7. Security Target Example - Key Management
- 13.1. Self-protection example
- 13.2. Self-protection reference architecture
- 13.3. FORMS reference model MAPE-K perspective
- 13.4. Autonomic computing
- 13.5. Self-protection taxonomy: Approach Positioning (“What”)
- 13.6. Self-protection taxonomy: Approach Characterization (“How”
- 13.7. DAI Lab/AOT security lecture modules
- 14.1. Use case diagram - Cybersecurity monintoring
- 14.2. Misuse case diagram - Cybersecurity monintoring
- 14.3. Security use case diagram - Cybersecurity monintoring
- 14.4. A system context diagram for the implemented system.
- 14.5. Container diagram for the example implementation.
- 15.1. The Internet
- 15.2. Intersection example
- 2.1. Dependencies needed by Quarkus in the pom.xml file to use Kubernetes
- 2.2. Microservice certificate request
- 2.3. Properties for using the microservice certificate
- 2.4. Get TLS certificate certificate chain from the secret and print it with openssl
- 2.5. The certificate chain used during TLS for a microservice
- 2.6. Configure HTTPS for ingress paths
- 2.7. The certificate chain for microservice ingress
- A.1. Create a Quarkus project
- B.1. Use Helm to install cert-manager
- B.2. Create cluster and namespace issuer/CAs
- B.3. Self-signed root
- B.4. Definition for a cluster issuer
- B.5. A cluster issuer certificate
- B.6. Root CA secret
- B.7. PEM encoded X.509 certificate
- B.8. X.509 cluster issuer certificate (shown with openssl x509 -text)
- B.9. Create a default ingress certificate
- B.10.Install Kyverno and apply policy
- B.11.Install trust-manager
- B.12.Configure Quarkus to trust our root CA(s).

## Part I.

# Lecture Notes

##### 1

## 1. Introduction

```
Goals
```

- Lean about our lecture goal of secure development practices for creating
  self-adaptive systems with microservices.
- Good software engineering practices help with creating secure software.
- Understand how security must be addressed in the software development
  life-cycle and why this is important for secure applications and network
  environments.
- Understand challenges in developing distributed systems.
- Be familiar with basic software development and security terms.
- Understand what “autonomous systems” are and what special security as-
  pects must be considered.

Digitization, autonomous vehicles, and artificial intelligence applications are hot topics
addressed in research activities and play an essential role in developing software products
and services. The dependence on digital processes and services also creates risks. Adver-
saries may target them to disrupt public life. A classic example is the distributed denial
of service (DDoS) attack on Estonia in April 2007 [9, 24], with many more incidents
since. An example closer to home and more recent was the attack on the Technische
Universität Berlin in 2021^1. The effects of this could still be felt more than a year later
and are, I hope, a thing of the past when you read this paragraph.
In my personal opinion, many security incidents occur because it is too easy to create
a running program, and I am not alone in this:

```
“It is fairly easy to write computer programs without using software engi-
neering methods and techniques.” [42]
```

I believe the lack of application of sound engineering principles is a common cause
of many successful attacks. In my opinion, ignoring these principles very likely leads
to vulnerabilities. Many security solutions exist that help secure IT infrastructures.
However, these solutions cannot combat the root cause of many security vulnerabilities:
ignoring security while developing applications, services, and protocols. Development

(^1) https://www.tagesspiegel.de/berlin/unbekannte-attackieren-computersysteme-der-tu-berlin-5397220.
html

##### 2

1. Introduction IV Software Security for Autonomous Systems

teams must follow sound engineering principles, which require considering security during
requirements gathering, design, development, testing, deployment, and operation. How
they can do this will be the main focus of this lecture.

### 1.1. Autonomous systems

Sound engineering principles are especially important for autonomous systems. These
software systems, which may be combined with physical systems, act autonomously with
minimal or no human observation and intervention. Any faults or vulnerabilities in such
a system could have severe consequences, including life-threatening consequences in the
case of autonomous driving.
Standard software engineering and IT security practices also apply to autonomous
systems. In the lecture, we will focus on those fundamental practices and discuss them
only in the context of examples of autonomous systems. We will not discuss specific
aspects of developing autonomous systems; this will be reserved for other lectures.
Autonomous systems have two properties: They are distributed and employ some
artificial intelligence (AI) approach. Security and AI are something that we at DAI-
Lab/AOT address in the lecture “AI & Cybersecurity” during the summer semester. In
this course, we focus on the distributed nature of autonomous systems.

#### 1.1.1. Self-adaptive systems

In the lecture, we will pay more attention to self-adaptive systems than autonomous
ones, like self-driving cars. The main reason is that self-adaptive systems have two
properties, _self-protection_ and _self-healing,_ which are relevant for security.

#### 1.1.2. Distributed systems

Autonomous or self-adaptive systems can be realized as multiagent systems (MAS), a
special form of a distributed system. Distributed systems have many security challenges,
which will be a focus of the lecture.

#### 1.1.3. Microservices

Microservices and technologies from this area are a modern way to realize MAS and
a direction we at DAI-Lab/AOT are going. Therefore, this lecture will focus on the
security of microservices.

**But what are microservices?**
Figure 3.1 compares microservices with classical approaches to develop applications.
As you may guess from the picture, one task in developing services (as in customer-facing
(business) applications/services ) is to decompose our big, fuzzy idea of what we want
into smaller components (microservices). This decomposition is an essential part during

(^2) https://en.wikipedia.org/wiki/Big_ball_of_mud

1. Introduction IV Software Security for Autonomous Systems

```
Monolith
```

```
Module A Module B
```

```
Module C Module D Module E
```

```
Database
```

```
Microservice A
```

```
Database
```

```
Microservice B Microservice C
```

```
Database Database
a) b) c)
```

Figure 1.1.: A typical monolithic application contains all functionality in one “big ball
of mud”^2. This can be seen in a). For b) the big application was split
up into several modules that interact with each other to form the overall
application. In c), several self-contained components/processes only interact
through communication interfaces. Each microservice could be developed
independently and with its technology stack. The figure is inspired by [30]
.

the design phase^3. The main challenge here is to identify what can, should, or must be
separate from other components and can work independently. Since we are addressing
security in the lecture, this should also be a driving factor during decomposition. It
would be best to consider what should be isolated/hidden from other microservices and
kept together. More on this will be addressed in the design lectures.
For this lecture, a microservice is a small application (deployed to a local Kubernetes
cluster and executed there) that serves a specific task. Performing this task may rely on
communication and cooperation with other microservices. Everybody who visited one
of our MAS lectures may recognize some similarity to the definition of an agent.

```
Further reading
```

```
This lecture will not explore microservices, what they are, and how to develop
them. As an introduction, I recommend reading Building Microservices [30], Mi-
croservices Patterns [32] a , and for the relationship to our container technologies
and our homework assignments Kubernetes Native Microservices with Quarkus
and MicroProfile [7].
a Or alternatively simply browse the web page behind the book:https://microservices.io/
```

(^3) Which also means this will impact grading in homework assignments

1. Introduction IV Software Security for Autonomous Systems

#### 1.1.4. Securely developing microservices

During the lecture, we will discuss how developing microservices can be approached and
what is important regarding security. This includes but is not limited to the following
questions and topics:

- What are the general security requirements for my overall application/system?
  How does that affect the decomposition into microservices and the security re-
  quirements for each microservice?
- How to structure an application or service into microservices? What different
  design approaches exist to incorporate security enforcement and decisions into a
  collection of microservices? What kind of trust relationships and delegations of
  rights exist?
- How to enforce security policies? What standards, technologies, and components
  play a role in this?
- How to keep information secret, identify who a microservice is talking to?
- How to deploy everything and operate securely?

```
Further reading
```

```
For a more in-depth discussion of security and microservices, I recommend the fol-
lowing books Secure by Design [20], API Security in Action [26], and Microservices
Security in Action [41]. The first two books do not directly deal with microservices
but contain content that should be considered when developing microservices.
```

### 1.2. Security and the software development life-cycle

To answer the previous questions, we do not have to invent anything new; we need to
follow software engineering principles.
A fundamental concept of software engineering is that software creation has different
phases. These phases are typically called the software development life-cycle (SDLC)).
The waterfall model is a classic example of an SDLC (see 1.2). For a more in-depth
discussion of the SDLC and general approaches to developing software, see Chapters 2
(Software Processes) and 3 (Agile Software Development) in [42].
In the lecture, we will examine each phase and discuss what needs to be done regarding
security and why considering security at each step of the way is essential. So, what can
you expect in the following lectures?

**Requirements** Here, we need to gather security requirements and try to resolve conflicts
with other requirements. This include considering which adversaries, threats, as-
sets, and risks need to be considered.

1. Introduction IV Software Security for Autonomous Systems

```
Requirements
definition
System and
software design
Implementation
and unit testing
Integration and
system testing
Operation and
maintance
```

Figure 1.2.: The waterfall is a classical example for a SDLC. Modern approaches focus
more on an iterative approach, may use different names for the phases, and
conduct them in shorter time cycles. The figure is based on a figure in [42].
This book is also a good source if you need to catch up on the fundamentals
of software engineering.

**Design** should enable _Security by Design_ During the design phase, we create the overall
architecture of our solution and how certain components or features should be
realized. This step must also include security considerations, e.g., the inclusion
of _choke points_ , relationships between components, information flows, visibility
and privileges, and what responsibilities a component has. Furthermore, we must
consider how and where access control needs to be enforced.

**Development** During the actual coding phase, we need to observe good development
practices to avoid mistakes that may lead to vulnerabilities. A typical example of
this is the lack of input validation that may lead to buffer overflows.

**Testing** During testing, we should not only consider the regular behavior and the re-
quired functionality, but we also need tests that focus on security issues. This does
mean conducting penetration tests but also includes security aspects in unit tests.

**Deployment & Operation** Finally, an application or service needs to be deployed, ex-
ecuted, and maintained in an operational environment. These activities without
security can also open up attack vectors to an adversary. Insecure operation and
maintenance practices may circumvent security measures that are part of an ap-
plication or service.
For this lecture, we do not consider the SDLC for every possible application and
service. But we focus on solutions that operate autonomously and are distributed.

### 1.3. IT security: Basic goals and concepts

We discussed gathering security requirements, securely developing microservices, and
how security needs to be included in the phases of the SDLC. But we should first recall
(or address) what security typically is about. There are three main security goals:
confidentiality, integrity, availability (CIA). Besides these goals, there are many other
security goals, but these three are at the core of all security considerations.

1. Introduction IV Software Security for Autonomous Systems

**CIA: The three main security goals**

**Confidentiality** A central goal is to keep information secret from other parties, and only
authorized entities are allowed to see this information. To conceal data, one could
use cryptography. Other parties may still observe that some information is present
but cannot see what it is since this is kept secret. Sometimes, secrecy alone is not
enough; here, it may be required to completely hide the existence of information.
Steganography, for example, could be used to achieve this.

**Integrity** Information or data may be kept confidential or visible to everyone. In both
cases, it may be required that it is possible to detect modification of data. With
methods for integrity, we address this issue; one possible approach is the use of
hash functions.

**Availability** The third and final goal addresses issues surrounding guaranteeing access
to resources and assets. This goes beyond data and includes (business) processes
and system functionality. Here, we also should consider how a secure state can be
ensured, even with partial or total system failures.

We will need some basic common vocabulary When we want to discuss our security
goals in more detail. We need to be able to describe what needs to be protected and
what this protection is against. Furthermore, we may need ways to express how likely
such a comprise will be and how damaging it is. Figure 1.3 provides an overview of the
most basic terms and their relationships.
Foremost when we think and talk about security^4 we need to consider who our _adver-
sary_^5 is. This entity (person, group, or institution) is the main source of threats against
our assets. The _asset_ is what has value to its owner and needs to be protected against
adversaries and threats originating from them. When we think about assets, we have to
think about potential harm to them; there will be more than one _threat_ to consider, and
they may be different for all relevant adversaries. Typically, we use threat modeling to
identify threats and threat actors.
Ultimately, we will find multiple threats and need a way to decide how to prioritize
them. We must start thinking about risks. With risk values, we express the likelihood of
a threat to materialize and the potential damage. This requires us to know about asset
values and an idea of existing vulnerabilities and allow a threat to occur. A vulnerability
is a weakness^6 that exists in a product (software or hardware). During an attack, an
adversary may use an exploit to utilize a vulnerability for gaining access to a system or
other purposes enabled by the exploit and vulnerability. Finally, an adversary will not

(^4) Hopefully, also in that order.
(^5) Alternatively the terms _threat actor_ or _attacker_ are often used.
(^6) weakness vs. vulnerability: It is important to understand that there is a difference between weakness
and vulnerability. Weaknesses are general security problems that can be integrated into a product
during development.
An example is the potential for a buffer overflow by not checking or ensuring that character arrays
do not exceed a specified size. A vulnerability, on the other hand, is a specific instance of a weakness
in a concrete product (and product version).

1. Introduction IV Software Security for Autonomous Systems

```
Asset
```

```
Security Controls
```

```
User
```

```
Adversary Vulnerability
```

```
Uses Exploit
```

```
is a Threat to
```

```
Risk
```

```
Security Controls Bypasses has
```

Figure 1.3.: Security goals typically use some common terms. These terms also do not
stand alone but are related. This figure represents a simplified view of how
an adversary may compromise our security goals.

conduct only one activity, e.g., using an exploit, but multiple steps must be performed
to achieve a goal. Common approaches to achieving goals are described with attack
patterns.

```
Information sources
CWE Common Weakness Enumeration (CWE™- https://cwe.mitre.org/) is
a list of weakness types in software and hardware.
```

```
CVE Common Weakness Enumeration (CVE®- https://www.cve.org/) is a col-
lection of existing vulnerabilities in software and hardware products.
```

```
CAPEC Common Attack Pattern Enumerations and Classifications (CAPEC™-
https://capec.mitre.org/) is a collection of attack patterns adversaries
may use to attack an application or network.
```

```
MITRE ATT&CK Is a collection of tactics, techniques, and procedures (TTP)
that adversaries may use in a campaign against a target. Each technique
is typically based on one or more attack patterns (with a link to CAPEC)
or a weakness (with a link to CWE). The ATT&CK matrix for enterprises
is available at attack.mitre.org; besides this matrix, it also has one for
industrial control systems and mobile devices.
```

1. Introduction IV Software Security for Autonomous Systems

### 1.4. Summary

This introduction chapter introduced the relationship between SDLC and security. We
talked about basic concepts, terms, and information sources and presented an example
we will use to illustrate the content of this lecture. 1.4 presents an overview of how the
security can be incorporated into the SDLC, and figure 1.5 shows what we will discuss
in the lecture in this regard,

```
Figure 1.4.: Source: [17]
```

```
Requirements definition System and softwaredesign Implementation and unittesting Integration and systemtesting Operation andmaintance
```

```
Security requirements
Misuse casesAttack trees 
```

```
Security designpatterns
Using patternsSoftware
System
```

```
Implementation
Clean codeStatic code analysis
Testing
Unit tests
```

```
Testing
FuzzingPenetration testing
Kubernetes
Monitoring &Oberservability
```

```
Figure 1.5.: Security, SDLC, and topics addressed in the lecture.
```

## 2. Cryptography

```
Goals
```

- Understand how cryptography helps in achieving security goals.
- Understand the basic principles of symmetric and asymmetric encryption
  and their advantages and drawbacks.
- Be able to describe the process of generating digital signatures.
- Be able to link the theory to practical use cases in developing and operating
  distributed applications.

Before addressing the SDLC and the lecture content in more detail, we need to discuss
some cryptography basics since they are relevant for the remaining lecture and the
homework tasks. Cryptography is part of the field of cryptology, but in the lecture, we
will not discuss anything related to cryptanalysis.

### 2.1. Introduction

Confidentiality, integrity, and availability are the three basic security goals we dis-
cussed last week. This week, we will focus on _confidentiality_ and _integrity_ and see how
cryptography helps achieve these goals.
With symmetric encryption and asymmetric, we can achieve confidentiality. Hash
functions and digital signatures can be used to ensure integrity.
Symmetric and asymmetric encryption, hash algorithms, and digital signatures will
play a role in the TLS protocol, will be used for authentication and authorization, and
will be needed for digital certificates and tokens.
We will need random numbers for cryptographic keys, so we need to talk about Pseudo-
random number generation. For asymmetric encryption, we need a way to link public
keys with some other information, for example, identities. This means we have to look
into public key infrastructure (PKI) and the X.509 standards.

### 2.2. Integrity

With integrity, we want to be able to detect changes in transmitted data. Either be-
cause an adversary forged the amount of money to be transferred or because permissions
were changed in the access token.

##### 10

2. Cryptography IV Software Security for Autonomous Systems

Of course, the question is how we achieve this. How can this be done with little
overhead for computational resources and data (message) size? Furthermore, how can
this be done securely to ensure that data is not modified?
There are various approaches to this, but we will focus only on one widespread ap-
proach. For more approaches, see the books mentioned last week and those mentioned
at the end.
We will only look at hash functions and only discuss the most basic concepts for them.
The main ideas behind a hash function are:

- Condenses an arbitrary message to a fixed size: _h_ = _H_ ( _M_ )
- It is usually assumed that the hash function is public and not keyed.
- Hash is used to detect changes to a message, and it is hard to forge messages to
  generate the same hash value.

There are various ways to ensure the secure exchange of messages and hash value;
some require encryption, and some require security. Figure 2.1 shows one example that
guarantees integrity and encrypts only the hash value.

```
Figure 2.1.: Only Message Authentication (Integrity. Source: [44]
```

```
To realize the ideas behind a hash function, it needs to fulfill some requirements:
```

- _H_ can be applied to any sized message _M_
- _H_ produces fixed-length output _h_
- _h_ = _H_ ( _M_ ) is relatively easy to compute for any message _M_
- Given _h_ , it is computationally infeasible to find _x_ s.t. _H_ ( _x_ ) = _h_ ( **one-way prop-**
  **erty** )
- Given _x_ , it is computationally infeasible to find _y_ s.t. _H_ ( _y_ ) = _H_ ( _x_ ) ( **weak colli-**
  **sion resistance** )
- It is computationally infeasible to find any _x, y_ s.t. _H_ ( _y_ ) = _H_ ( _x_ )( **strong collision**
  **resistance** )

2. Cryptography IV Software Security for Autonomous Systems

Various algorithms for hash functions exist, but even though they have fulfilled the
mentioned properties at some point, this may not always be true. Besides flaws in the
algorithm (or its implementation), they may be outdated due to available computational
resources, which break one or all of the three last requirements. For example, this
happened in the past to SHA-1.
We usually do not use hash functions alone in our scenarios for the lecture example
and the homework tasks. We typically want to achieve integrity and confidentiality.
Figure 2.2 shows one example of combining hash functions with encryption.

```
Figure 2.2.: Message Authentication (Integrity) & Confidentiality. Source: [44]
```

Having addressed integrity with hash functions, we must look closer at methods for
achieving confidentiality.

### 2.3. Confidentiality

For us, confidentiality is mainly about network security and transmitting a message from
a sender to a recipient. Although, the methods for achieving confidentiality can also be
applied to data at rest.

Figure 2.3.: A simple model for transmitting information from sender to recipient and
protecting it from an adversary. Source: [44]

2. Cryptography IV Software Security for Autonomous Systems

In figure 2.3, we see a simple message transmission model between sender and recipient.
This model aims to prevent attacks by an adversary on the message. These attacks could
be, for example, either eavesdropping or message alteration.
Our interest lies in transforming a _message_ into a _secure message_. For the past thou-
sands of years, this was achieved with _symmetric_ cipher approaches, and since a few
decades, with _asymmetric_ approaches.
The main difference between the two classes of approaches is the number of keys they
require.

**symmetric cipher** Communication partners share a secret (key).

**asymmetric cipher** Communication partners own each a private (secret) and a public
key.

```
Before we proceed, we must define a few basic cryptography terms:
```

**plain text** The original message.

**cipher text** The coded message.

**cipher** Algorithm for transforming plain text to cipher text.

**key** Information used in cipher, known only to sender and/or receiver.

**encipher/encrypt** Converting plain text to cipher text.

**decipher/decrypt** Recovering plain text from cipher text.

**cryptography** Study of encryption principles/methods.

**cryptanalysis (codebreaking)** The study of principles/methods of deciphering cipher
text without knowing the key.

**cryptology** The field of both cryptography and cryptanalysis.

Formally, we can define cryptographic systems (ciphers), see figure2.4, as a five-tuple
( _P, C, K, E, D_ ), with:

_P_ is a finite set of plain texts

_C_ is a finite set of cipher texts

_K_ is a finite set of keys

_EK_ is a transformation _P_ → _C_

_DK_ is a transformation _C_ → _P_

```
For this cryptographic system, the following shall be true: DK ( EK ( M )) = M
One important property for every cryptographic system is Kerckhoffs’s principle:
```

2. Cryptography IV Software Security for Autonomous Systems

```
Figure 2.4.: Cryptosystem. Source: [44]
```

```
“A cryptosystem should be secure even if everything about the system, except
the key, is public knowledge.”^1
```

Although this principle dates back to 1883, cryptographic systems are still developed
and marketed without applying it, which caused repeated security failures.
Before going into detail about symmetric and asymmetric ciphers, we have to discuss
the security of cryptographic methods. We distinguish between _unconditional security_
and _computational security_.
A cryptographic method is considered unconditionally secure when it does not matter
how much computational power and time is available since the cipher text does not
provide sufficient information to determine the corresponding plain text.
Computational security means a cipher cannot be broken with limited state-of-the-art
computing resources. This means, for example, that the time for calculations is greater
than the universe’s age.
Quantum computing is seen as a threat to existing cryptographic methods since most
of them are (today) computationally secure, but maybe not anymore with quantum
computers. Unfortunately, unconditional security is hard to achieve, especially if the
system should be easy to use. See [44] for background and an example of unconditional
security.

#### 2.3.1. Symmetric ciphers

Symmetric ciphers go back to the time of Cesar, and since then only changed in the
complexity of substitutions.^2 The name symmetric cipher is derived from the fact that
a sender and a recipient need the same (secret) key to encrypt and decrypt a message.

(^1) [http://en.wikipedia.org/wiki/Kerckhoffs%27s_principle](http://en.wikipedia.org/wiki/Kerckhoffs%27s_principle) (last accessed 2018-11-18)
(^2) This a gross oversimplification, if you are interested in the history of ciphers see [21] or see [35] and
[44] for details on historical and current algorithms.

2. Cryptography IV Software Security for Autonomous Systems

A core problem of symmetric ciphers is the need for a secure communication channel
to exchange a key to communicate securely. Later, we will see that asymmetric ciphers
may be a solution. Still, they have only been around since the 1970s.^3
Figure 2.5 provides an overview of how a symmetric cipher works.

```
Figure 2.5.: Symmetric Cipher Model. Source: [44]
```

On the most basic level, we require a plain text and a secret key as input for the
encryption algorithm that produces a cipher text. For the decryption, we need this
cipher text, and again, the secret key has an input, and we will get the plain text as a
result.
We have two kinds of symmetric ciphers: _stream_ and _block_ ciphers. For example, the
Advanced Encryption Standard (AES) cipher is such a block cipher and is currently
widely used.

```
Figure 2.6.: The ECB Pinguin^4
```

```
Since block lengths rarely match the actual message size, we need a way to fill in the
```

(^3) Again, a bit of history: In the 1970s, Diffie and Hellman published their algorithm, but according to
[40], other researchers developed similar approaches before Diffie and Hellman. However, the results
were not made public since their work was done for British intelligence services. It took a few decades
before this was declassified.

2. Cryptography IV Software Security for Autonomous Systems

missing content for blocks. Several modes of operation exist, which we will not discuss
in detail. However, these modes of operation are still important and may lead to useless
encryption. See the encrypted image in figure 2.6 and guess what the original image
was.
To summarize, if we want to use symmetric ciphers, we need the following:

- A strong encryption algorithm. Which is public since only the key needs to be
  kept secret.
- A secret key known only to the sender **and** receiver.
  **-** _Y_ = _EK_ ( _X_ )
  **-** _X_ = _DK_ ( _Y_ )
- A secure channel for key distribution is needed.

#### 2.3.2. Asymmetric Ciphers

Symmetric ciphers need a secure channel to distribute the shared secret. A consequence
of this is that our distributed applications require a secure (offline) way to distribute
secret keys; this is a problem.
One use case for asymmetric encryption is that it can provide a secure channel to
exchange secret keys.

**Asymmetric Ciphers**
An important difference between symmetric and asymmetric ciphers is that we now
have different keys for encryption and decryption.

- _Y_ = _EK_ 1 ( _X_ )
- _X_ = _DK_ 2 ( _Y_ )
- _K_ 1 is called public key ( _KP ub_ )
- _K_ 2 is call private key ( _KP ri_ )

```
For asymmetric ciphers, we usually have two use cases attached to a key:
```

**public key** may be known by anybody and can be used to _encrypt messages_ and _verify
signatures_

**private key** known only to the owner/recipient, used to _decrypt messages_ and _sign (cre-
ate) signatures_
For asymmetric ciphers, we have the following general requirements:

(^4) Picture was taken from https://blog.filippo.io/the-ecb-penguin/, Attribution-ShareAlike 4.0
International (CC BY-SA 4.0) https://creativecommons.org/licenses/by-sa/4.0/.

2. Cryptography IV Software Security for Autonomous Systems - It is computationally easy to generate public/private key pairs _KP ub_ and _KP ri_. - Given message _M_ and public key _KP ub_ , It is computationally easy to generate the
   cipher text _C_ : _C_ = _E_ ( _KP ub, M_ ) - Given cipher text _C_ and private key _KP ri_ , it is computationally easy to recover
   original message _M_. - Given public key _KP ub_ , it is computationally infeasible to determine the private
   key _KP ri_. - Given cipher text _C_ and public key _KP ub_ , it is computationally infeasible to recover
   original message _M_.
   The mathematical problems behind asymmetric ciphers and concrete algorithms for
   them are out of the scope of this lecture. If you are interested in this topic, I suggest
   you read [35]. As mentioned earlier, when discussing computational security, quantum
   computing may pose a problem if the underlying mathematical problems can be solved
   quickly with quantum computers.

Figure 2.7.: Process for encryption and decryption with an asymmetric cipher. Source:
[44]

Figure 2.7 shows how encryption with asymmetric ciphers works. We have a sender
and recipient, usually named Alice and Bob, who want to talk to each other. This
means a sender (Bob) must determine what key he needs when communicating with
Alice. Bob owns a collection of public keys (key ring) and selects the _public key_ for Alice
from this collection. The plain text and the public key for Alice are needed as input
for the encryption step in an asymmetric cipher. The produced cipher text can then be
transmitted to Alice. She takes her _private key_ and the cipher text and uses both as
input for the decryption step of the asymmetric cipher, which produces the plain text
again.

2. Cryptography IV Software Security for Autonomous Systems

```
Figure 2.8.: Process of authenticating the sender of a message. Source: [44]
```

Since we can use asymmetric ciphers for encryption and decryption and can also create
digital signatures, the question arises: Why do we still care about symmetric ciphers?
We use asymmetric ciphers for symmetric key exchange/agreement because encryption
and decryption operations with symmetric ciphers require fewer computational resources
than we would need with asymmetric ciphers.
This is partly related to the key sizes for symmetric and asymmetric ciphers. Small
keys for symmetric ciphers achieve a similar level of computational security as longer
keys for asymmetric ciphers. See table 2.1 for comparing key sizes.

**Key size comparison**

```
Symmetric
(key size in bits)
```

```
Elliptic curves
(size in n in bits)
```

##### RSA

```
(modulus size in bits)
56 112 512
80 160 1024
112 224 2048
128 256 3072
192 384 7680
256 512 15360
```

```
Table 2.1.: A comparison of key sizes for symmetric and asymmetric ciphers.
```

### 2.4. Digital signatures

One common security goal we did not discuss last week is _non-repudiation_. This goal
concerns how a party cannot deny a message was created by it or exchanged with another

2. Cryptography IV Software Security for Autonomous Systems

party. Asymmetric ciphers provide us with a tool to solve this by creating a digital
signature.
Digital signatures provide the ability to:

- Verify author, date & time of signature
- Authenticate message contents
- Verification by third parties to resolve disputes
  Figure 2.9 presents two views on generating and verifying signatures. We can have
  Bob, who wants to talk to Alice. This time, he also wants Alice to be sure the message
  is from him. He can achieve this by digitally signing his message. Creation of the digital
  signature involves the following steps:

1. Bob creates a message _M_.
2. Bob creates a fixed-length hash value for _M_ by using a hash function: _h_ = _hash_ ( _M_ )
3. To sign the message _M_ Bob encrypts it the hash value _h_ with his private key
   _KP rivBob_ : _S_ = _E_ ( _KP rivBob, M_ )
4. He now can transmit _M_ and _S_ to Alice, ideally by encrypting both with the public
   key of Alice: _C_ = _E_ ( _EP ubAlice,_ ( _M, S_ ))

```
Figure 13.1 Generic Model of Digital Signature Process
```

```
signatureDigital
generationalgorithm
```

```
privateBob’s
key Bob’spublickey
```

```
signatureBob’s
for M
```

```
S
```

```
Message
```

```
M
```

```
signatureDigital
verificationalgorithm
```

```
signatureReturn
valid or not valid
```

```
Bob Transmit Alice
```

```
S
```

```
Message
```

```
M
```

```
(a) Overview
```

```
Figure 13.2 Simplified Depiction of EssentialElements of Digital Signature Process
```

```
Encrypt Compare
```

```
privateBob’s
key
```

```
Bob’spublic
key
```

```
signatureBob’s
for M
```

```
S
```

```
Message M
```

```
Bob Alice
```

```
Cryptographichash
function
h
```

```
Message M
Cryptographichash
function
h
```

```
Decrypt
h ’
```

```
S
```

```
signatureReturn
valid or not valid
```

```
(b) Simplified process.
```

```
Figure 2.9.: Digital Signatures Source: [44]
```

When Alice receives the encrypted message from Bob, she performs the following
steps:

2. Cryptography IV Software Security for Autonomous Systems
   1. First, she must recover the plain text from the transmitted cipher test; for this,
      she needs her private key: ( _M, S_ ) = _D_ ( _KP rivAllice, C_ )
   2. To verify the signature, she needs the computed hash value from Bob. This means
      she needs to decrypt _S_ with the public key of Bob, which will yield _h_ ′. _h_ ′ =
      _D_ ( _KP ubBob,S_ )
   3. She also needs to create her own hash value _h_ for the message _M_ : _h_ = _hash_ ( _M_ )
   4. If _h_ and _h_ ′ are equal, she can be sure that the message was not altered and
      originated from Bob. Since only Bob knows the corresponding private key _KP rivBob_
      for the public key _KP ubBob_. Also, we, and Alice, know that it should be hard to
      create a different message with the same hash value.

```
We now have one problem: How do I know that a public key belongs to Alice or Bob?
```

### 2.5. Public key infrastructure

Our previous problem can be solved by combining a public key with identity-related
information. This combination is usually referred to as a certificate^5. We also need a
way to create certificates, manage, revoke, validate, and retrieve them. The concept of
a public key infrastructure is supposed to provide solutions for this. The lecture focuses
on the most common standard, X.509. We ignore in the lecture concepts like the web of
trust that we have with PGP, which tries to solve the tasks differently.

#### 2.5.1. Certification Authority

In X.509, a public key is combined with an identity and other information. An X.509
certificate is created and digitally signed by a certification authority (CA). Usually,
in X.509 PKI, we have a hierarchy of CAs. Starting from root CA with a self-signed
certificate and multiple intermediate CAs with certificates signed by the CA in the chain
above. Finally, at the end of this chain is a certificate for an end entity. This certificate
chain will also play a role when discussing how to check if a certificate is valid.

- CA issues certificates to users
- Certificates can be placed in a public directory
- CAs must form a hierarchy
- Certificates link members of the hierarchy to validate other CAs
  **-** Each CA has certificates for clients (forward) and parent (backward)
  **-** Each client trusts parent certificates

(^5) The concept originated in a bachelor thesis [23], so what you can write in a bachelor
or master thesis can have a long-lasting impact. If you want to know more, you can
have a look at this blog article: https://medium.com/asecuritysite-when-bob-met-alice/
the-undergraduate-who-secured-the-internet-loren-kohnfelder-80ce6ed33c65

2. Cryptography IV Software Security for Autonomous Systems

#### 2.5.2. X.509 certificate and CRL

But what is in an X.509 certificate besides a public key and an identity?

```
Figure 2.10.: Certificate and Certificate Revocation List (CRL). Source: [44]
```

Figure 2.10 shows the contents of an X.509 certificate; if you are interested in all the
details, I refer you to [8]. For the lecture, we focus on some crucial fields:

**Issuer name** An X.500 name identifying the issuer of the certificate

**Subject name** An X.500 name^6 identifying the person or service to which the certificate
belongs to.

**Period of validity** The period is defined by a start and end date, and only during this
period the certificate is valid.

**Extensions** Defines constraints and additional information for a certificate. Extensions
can be either _critical_ or _non-critical_ ; all critical extensions MUST be checked
during certificate validation. The validation MUST fail if a validation method
does not support an extension but is marked critical.
**Subject alternative name** Since the X.509 name is, in practice, not very useful,
we need the option to define additional names, e.g., e-mail addresses or host-
names.
**Key usage** This extension allows the restriction of what a key can be used, e.g.,
only for digital signatures or encryption.
**Basic constraints** With this extension, we can mark a certificate as belonging to
a CA and restrict the certificate chain’s length.

Figure 2.10 also shows how information about revoked certificates can be distributed.
For this, a certification revocation list (CRL) is used. A CRL contains the following:

(^6) For example: C=DE, O=TU Berlin, OU=AOT, CN=Karsten Bsufka

2. Cryptography IV Software Security for Autonomous Systems
   - Identification of the issuing CA.
   - List of revoked certificates and revocation date.
   - Reason for revocation could be, for example:
     **-** User’s private key is compromised
     **-** User is no longer certified by this CA
     **-** CA’s certificate is compromised
   - Dates for the current and next updates for a CRL.
   - A CRL might be a complete list or only contain the difference from the last update.

X.509 certificates and CRLs are also good examples of the importance of digital sig-
natures. The issuer signs the content of a certificate and a CRL, which allows users of
a certificate or a CRL to verify the issuer and that the content has not been modified.

#### 2.5.3. X.509 certificate validation

If we want to use X.509 certificates, we must ensure they are valid. Otherwise, protocols
like TLS, which rely on certificates, would not make much sense. Validation contains
(at least) the following steps:

- Check if a certificate has been revoked. This can be done by checking the latest
  CRL or using online protocols.
- Check the validity period: Is the certificate already valid or expired?
- Is the signature correct?
- Does it meet all criteria defined in extensions? Are any of them critical, and do I
  support those critical extensions?
- In case a certificate contains hostnames as alternative names, did the correct host
  present the certificate?
- Is the whole chain of certificates, from the certificate I want to valid up to the root
  CA, valid?
- Do I trust the root certificate?

2. Cryptography IV Software Security for Autonomous Systems

### 2.6. Random Numbers

A critical aspect regarding cryptographic keys is that they require random numbers dur-
ing generation. Natural events are the best source of randomness, but this is impractical
on computers. So we need an alternative.
On computers, we do not use random numbers but pseudo-random numbers. The
difference between them is:

**Random** Output is not deterministic.

**Pseudo-random** Output is deterministic. It appears random to observers who do not
know the underlying determinism.

We use a pseudo-random number generator (PRNG) to generate random numbers in
software applications. For example, when we create our key pair for use in asymmetric
encryption. A PRNG is:

- Algorithmic technique to create “random numbers”
  **-** although not truly random
  **-** can pass many tests of “randomness”

Many programming languages and libraries provide the option to generate random
numbers, but they are not always sufficient for security purposes since the produced
pseudo-random numbers are weak. For Java, you must not use Random for security
purposes, but you should use SecureRandom, or other PRNGs, that create strong random
numbers. Also, generating pseudo-random numbers requires a good seed value. If this
is not carefully chosen and has a high entropy, generated pseudo-random numbers may
be guessable.

### Acknowledgments

This presentation is in part based on:

- Slides of W. Stallings and materials shared from his textbook web page.
- Cryptography: Theory and Practice by D.R. Stinson.
- Codes, Ciphers and Secret Writing by Martin Gardner.
- Applied Cryptography by Bruce Schneier.
- Lecture slides prepared by Dr. Seyit A. Camtepe for his TU Berlin class Communi-
  cation Network Security and during his TA-ship for the Network Security Lecture
  of Prof. B. Yener at RPI, New York.

2. Cryptography IV Software Security for Autonomous Systems

### 2.7. Kubernetes, Quarkus, and PKI for TLS

The previous chapter served mainly as theoretical background for using TLS with
Quarkus microservices in a cluster.
With TLS, we try to achieve confidentiality and integrity for communication with and
between microservices. We may also want to identify microservices or other applications
we communicate with. When we use TLS, we require key pairs and X.509 certificates. We
have several options for creating our own PKI for issuing and distributing certificates, or
obtaining certificates from an existing CA. We will not look at all these options but focus
on a common approach to handle this for a Kubernetes cluster. This means we will use
cert-manager^7 , trust-manager^8 , and Kyverno^9 to create and distribute our certificates
and private keys.

```
Figure 2.11.: Overview of cert-manager. Source: https://cert-manager.io/
```

```
cert-manager (see figure 2.11) provides us with various things we will need:
```

**CA** We can create CAs for our cluster.

**mTLS** With the created CAs, we can issue X.509 certificates for our microservices. This
means we can use mTLS between microservices or in a service mesh.

**TLS** We can issue certificates for endpoints and web applications. This means we can
establish TLS connections to REST API endpoints or HTTPS connections to web
applications. **Note:** To ensure your clients do not complain about untrusted CAs,
you need to export the root CA certificate and trust it in your browser or operating
system.

In appendix B, we will discuss how to add cert-manager to a Kind cluster; here, we
will only focus on requesting a certificate from cert-manager.

(^7) https://cert-manager.io/
(^8) https://cert-manager.io/docs/trust/trust-manager/
(^9) https://kyverno.io/

2. Cryptography IV Software Security for Autonomous Systems

(a) The initially generated PKI, when
the scripts from appendix B have
be executed. We have a root CA
(default-example-ca) in names-
pace cert-manager, and an in-
termediate CA (iv-ssas-ca) in
namespace ivssas-example.

```
(b) For our microservice
(hello-world) we need a certifi-
cate for the actual microservice
(hello-world-backend) and one
for ingress that exposes the service
(hello-world-service-tls-secret).
Both will be issued by the inter-
mediate CA.
```

```
Figure 2.12.: PKI and microservice certificates.
```

In figure 2.12b, we see the PKI we have created, the micorservice certificate (blue),
and the ingress certificate (red).
The blue certificate is only used inside the Kubernetes cluster, and the red certificate
will be used when a client, e.g., curl, accesses the microservice from outside the cluster.
When we later talk about software security patterns, we will see a pattern (API gateway)
that will remove the need to expose our microservices directly and create ingress certifi-
cates for them^10. Unfortunately, with Quarkus, we have two slightly different methods
for creating the red and blue certificates.

### 2.8. Create a TLS certificate for a microservice

When we want to create an X.509 certificate for a Quarkus microservice with cert-
manager, we have to define the request in a YAML file and configure the Quarkus
application^11 to use the created certificate. Furthermore, we must ensure that our Maven
project has all the required dependencies. Listing 2.1 shows these needed dependencies.
What might surprise you is that we do not use quarkus-kind. This is because it behaves
slightly differently when creating a Kubernetes YAML file, ignoring some configuration

(^10) Another option would be to enable TLS passthrough when the ingress controller is installed, but since
we may not be in the position to install the ingress controller, we will not address this in the lecture.
(^11) Configuration for this is usually located in src/main/resources/application.properties. For more
details in general on how to configure Quarkus applications, see https://quarkus.io/guides/
config-reference.

2. Cryptography IV Software Security for Autonomous Systems

```
options needed for homework assignments.
```

Listing 2.1: Dependencies needed by Quarkus in the pom.xml file to use Kubernetes
1 <dependenc i e s>
2 <dependency>
3 <g r oupId>i o. qua rkus</g r oupId>
4 <a r t i f ac t Id>qua rkus−kube rne t e s</a r t i f ac t Id>
5 </dependency>
6 <dependency>
7 <g r oupId>i o. qua rkus</g r oupId>
8 <a r t i f ac t Id>qua rkus−kube rne t es−con f i g</a r t i f ac t Id>
9 </dependency>
10 <dependency>
11 <g r oupId>i o. qua rkus</g r oupId>
12 <a r t i f ac t Id>qua rkus−cont a i ner− image−j i b</a r t i f ac t Id>
13 </dependency>
14 </dependenc i e s>

```
When we have included the Kubernetes dependencies, Quarkus creates a Kubernetes
YAML file and uses this file to deploy a pod to Kubernetes, generate a service resource
for it, and any additional resources we configure.
Anything not automatically generated by Quarkus can be added through an additional
YAML file, which must be placed in src/main/kubernetes and named kubernetes.yml.
Quarkus will merge this file with the automatically generated file. The result can be
found in the folder target/kubernetes.
We use this approach to request an X.509 certificate for our microservice; see the
listing 2.2.
```

Listing 2.2: Microservice certificate request
1 ---
2 ap iVe r s i on: c e rt−manage r. i o/v1
3 k i nd: Ce r t i f i ca t e
4 me t ada t a:
5 name: he l lo−wo r ld−backend
6 spe c:
7 commonName: he l lo−wo r ld−backend
8 dnsName s:
9 - he l lo−wo r l d
10 - qua rkus−examp l e. t e s t
11 encodeUs age s InReque s t: t rue
12 i sCA: f a l s e
13 i s sue rRe f:
14 k i nd: I s sue r
15 name: iv−s s as−examp l e−ca−i s sue r
16 s e c r e tName: he l lo−backend−t l s−s e c r e t
17 sub j e c t:
18 o r gan i za t i ons:
19 - TUB, AOT
20 us age s:
21 - s e rve r auth
22 - c l i ent auth

2. Cryptography IV Software Security for Autonomous Systems

```
For convenience, we define in line 5 a common name for the certificate. More critical
are the DNS names in lines 8 to 10. These are the potential hostnames by which our
microservice can be used and which will be used when verifying certificates during the
initial TLS protocol phases. Line 12 marks the certificate as an end entity certificate,
ensuring it cannot be used to issue valid certificates. Lines 20-22 further restrict the
usage to server and client authentication so it can be used for mutual TLS. In lines 13-
15, we define the issuer that shall issue the certificate; here, we select the intermediate
CA for the example, which is only allowed to issue certificates in a specific namespace.
Appendix B provides more details on the PKI set-up and issuer creation.
In line 16, we define the secret that will be used to store the create certificate (chain)
and private key. We need this name in the Quarkus configuration file^12. In listing 2.3,
we configure how this secret can be used for configuring TLS for a microservice.
```

Listing 2.3: Properties for using the microservice certificate
1 qua rku s. kube rne t e s. name=he l lo−wo r l d
2 qua rku s. kube rne t e s. name space= iv−s s as−examp l e
3
4 qua rku s. kube rne t es−con f i g. s e c r e t s. enab l ed=t rue
5 qua rku s. kube rne t es−con f i g. s e c r e ts=he l lo−backend−t l s−s e c r e t
6
7 qua rku s. kube rne t e s. s e c r et−vo l ume s. "he l lo−backend−t l s−s e c r et−vo l ume ". s e c r et−
name=he l lo−backend−t l s−s e c r e t
8 qua rku s. kube rne t e s. s e c r et−vo l ume s. "he l lo−backend−t l s−s e c r et−vo l ume ". de f au l
t−mode=0400
9 qua rku s. kube rne t e s .mount s. "he l lo−backend−t l s−s e c r et−vo l ume ". pa th=/e t c/ c e r t s
10 qua rku s. kube rne t e s .mount s. "he l lo−backend−t l s−s e c r et−vo l ume ". r ead−on ly=t rue
11
12 qua rku s. ht t p. i ns e cure−r eque s ts=d i s ab l ed
13 qua rku s. ht t p. s s l. c e r t i f i ca t e. f i l es=/e t c/ c e r t s/ t l s. c r t
14 qua rku s. ht t p. s s l. c e r t i f i ca t e. key−f i l es=/e t c/ c e r t s/ t l s. key

```
The Maven dependency quarkus-kubernetes-config allows us to use secrets and
config maps in Quarkus application properties. In lines 1 and 2, we define a name for
our pod and service resource and the namespace it will be deployed to. Important:
The namespace will not be generated by Quarkus and must exist before a deployment
is attempted. In lines 4 and 5, we tell Quarkus which secrets will be read. So far, it is
only the secret for the private key and certificate. This is done in a pod by mounting
the secrets as files. The secret key will be the file name, and the secret value will be the
file’s content. In lines 7 to 10, we define a volume for the secret and tell Quarkus how it
should be mounted in the pod.
In line 12, we tell Quarkus that only HTTPS requests are allowed for the REST
API endpoints. In lines 13 and 14, we configure the certificate and private key the
microservice will use; both are located in mounted files originating from the secret.
The nice thing about using cert-manager for this is that before our certificate expires, it
is automatically replaced by a new one without our intervention. Why is this important?
```

(^12) application.properties located in the folder src/main/resources

2. Cryptography IV Software Security for Autonomous Systems

```
We see this when we look at the validity periods in the listing. To produce the output
from that listing 2.5, we first need to execute the two commands in listing 2.4
```

```
Listing 2.4: Get TLS certificate certificate chain from the secret and print it with openssl
1 kube c t l ge t s e c r e t−name he l lo−backend−t l s−s e c r e t−n iv−s s as−examp l e−−t emp
l a te= ’ {{ i ndex␣. da t a␣ " t l s. c r t"}} ’ | ba s e64−d > t l s. pem
2
3 opens s l c r l2 pkc s7−noc r l−c e r t f i l e t l s. pem | opens s l pkc s7−pr i nt_c e r t s−t e
xt−noout
The commands above will produce the two certificates below. The first belongs to the
microservice (see the subject name in line 11), and the second to the intermediate CA
(see the subject name in line 49 and the CA flag in line 65). Also, note in lines 8-10
and 46-48 that the validity period is only a month. Manual certificate renewals would
be tedious. Luckily, cert-manager takes care of this.
During the TLS certificate exchange, the shown chain will be validated, which requires
the root CA to be included in the set of trusted certificates. Since we have our own root
CA, this is done with the help of trust-manager; see appendix B for details. If you
compare the microservice certificate (lines 1-27) with what we requested in listing 2.2,
you see that everything is there.
```

Listing 2.5: The certificate chain used during TLS for a microservice
1 Ce r t i f i ca t e :
2 Da t a:
3 Ve r s i o n: 3 (0 x2)
4 Se r i a l Numbe r :
5 e4:3 c : 0 3 : 0 1 : 1 1 : 0 4 : a9:85: c d: b7:89: d2:26: b f : 9 a: db
6 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
7 I s sue r : O=TU Be r l i n , OU=AOT, IV SSAS , CN=IV SSAS CA
8 Va l i d i ty
9 No t Be f o r e : Feb 18 09:18:29 2025 GMT
10 No t Af t e r : May 19 09:18:29 2025 GMT
11 Sub j e c t : O=TUB, AOT, CN=he l lo−wo r ld−backend
12 Sub j e c t Pub l i c Key In f o:
13 Pub l i c Key Al go r i thm: id−e cPub l i cKey
14 Pub l i c−Ke y: (256 b i t)
15 pub:
16 04:76: d1:76: b a : 4 6 : e9: f 9 : f 9 : e d: c 7 : 1 5 : e7: b2: f f :
17 5 c : 7 8 : 7 6 : c9: d1: e4: b8:15:2 c:2 a : 3 4 : a f : 7 0 : 2 c:5 f :
18 0 5 : 5 4 : 6 6 : f 0 : 0 5 : 2 b : 9 4 : 2 6 : 6 8 : e d:5 a:31:1 e:1 e : 4 0 :
19 d c : 9 7 : 4 b: a0:03: b4:47:7 e:6 b: e c : c4:5 d:0 c : 5 4 : 1 4 :
20 5 b : 4 9 : 8 8 : 5 9 : 7 d
21 ASN1 OI D: pr ime256v1
22 NIST CURVE: P− 256
23 X509v3 ext ens i on s :
24 X509v3 Ext ended Key Us ag e :
25 TLS Web Se rve r Authent i ca t i on , TLS Web Cl i ent Authent i ca t i o
n
26 X509v3 Ba s i c Cons t r a i nt s : c r i t i ca l
27 CA:FALSE
28 X509v3 Autho r i ty Key Ident i f i e r :

2. Cryptography IV Software Security for Autonomous Systems

29 29:DB:D4:4 B:60:4 E:98:AE: A9:0B:B5: C0: F9:5 B:6 F:BC:9 A:05:5 C:1A
30 X509v3 Sub j e c t Al t e rna t i ve Name :
31 DNS:he l lo−wo r l d , DNS: qua rkus−examp l e. t e s t
32 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
33 S i gna tur e Va l u e :
34 3 0 : 4 4 : 0 2 : 2 0 : 2 a: a a : 4 6 : f 6 : 8 5 : 6 7 : 8 5 : 8 f : 9 3 : e b:9 b:4 e : db: a2:
35 a0:2 b:5 e : 2 7 : c a : 9 1 : b2:6 a:2 f : dd: a f : 4 b:41:4 b : 0 1 : 5 7 : a c : d4:
36 0 2 : 2 0 : 6 3 : 6 a : 2 1 : c9: 76:6 a : 0 4 : 5 1 : f 5 : 6 b:35: a4: e a : 4 1 : e f : d f :
37 f c : e2:4 d:36: b d:29:8 d: e e:7 d:7 d: e0:5 c : a2:50:22:31
38
39 Ce r t i f i ca t e :
40 Da t a:
41 Ve r s i o n: 3 (0 x2)
42 Se r i a l Numbe r :
43 4 c : 5 6 : 5 3 : 2 a:2 a:0 d:57:6 a: c2: c b:01: f 5 : b4:3 e:7 b:5 e
44 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
45 I s sue r : O=TU Be r l i n , OU=AOT, CN=De f au l t CA IV SSAS Examp l e Cl us t e r
46 Va l i d i ty
47 No t Be f o r e : Feb 18 09:13:31 2025 GMT
48 No t Af t e r : May 19 09:13:31 2025 GMT
49 Sub j e c t : O=TU Be r l i n , OU=AOT, IV SSAS , CN=IV SSAS CA
50 Sub j e c t Pub l i c Key In f o:
51 Pub l i c Key Al go r i thm: id−e cPub l i cKey
52 Pub l i c−Ke y: (256 b i t)
53 pub:
54 04:2 b:56: e b:4 e:2 c : 0 3 : 4 e:3 e:1 f : 0 f : a8:97:6 d:10:
55 7 d:20: d1:1 d:0 d: c2:0 e : a b:b a:2 a:5 a : 1 2 : 7 0 : e f : f 1 :
56 e f : 6 c : 1 3 : 7 3 : e7:97: 4 d:2 f : 6 5 : e9:1 e : 0 1 : d a : 7 0 : c5:
57 72: a e : 7 8 : 9 d: a0:8 e : c5: f 3 : c d:48: c d: f 2 : d 4 : 5 3 : 5 3 :
58 7 d: f 8 : c 2 : 3 0 : d5
59 ASN1 OI D: pr ime256v1
60 NIST CURVE: P− 256
61 X509v3 ext ens i on s :
62 X509v3 Key Us ag e : c r i t i ca l
63 Di g i t a l S i gna tur e , Key Enc i phe rmen t , Ce r t i f i ca t e S i gn
64 X509v3 Ba s i c Cons t r a i nt s : c r i t i ca l
65 CA:TRUE
66 X509v3 Sub j e c t Key Ident i f i e r :
67 29:DB:D4:4 B:60:4 E:98:AE: A9:0B:B5: C0: F9:5 B:6 F:BC:9 A:05:5 C:1A
68 X509v3 Autho r i ty Key Ident i f i e r :
69 1D:9E:4 D:5B:BD:86:93:40:63:7 B:3D:E0: E6:7 A:4D:F0: A6:37: D4:B1
70 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
71 S i gna tur e Va l u e :
72 3 0 : 4 5 : 0 2 : 2 0 : 3 c : 0 8 : 7 e : e0:6 d : 0 3 : 6 0 : b 8 : 9 4 : 6 9 : b0: c3: c a: c6:
73 48:2 b: f 8 : 8 9 : b4:79: a c : f 2 : 8 1 : 9 1 : 9 c : b3: d 3 : 0 7 : 2 1 : 1 6 : 8 4 : b a:
74 0 2 : 2 1 : 0 0 : d9: e a:9 b : 3 9 : 1 9 : 9 b : 9 7 : 6 4 : 9 4 : c d: a0:6 b:44:5 c:4 c :
75 79: f 3 : e1: f 4 : 2 7 : 4 b:0 f : 4 f : a7: c e : 5 4 : 1 0 : db:4 d: a7:24: d8

```
One problem remains: when we expose our REST API endpoints through an ingress
path to the world outside our Kubernetes cluster, these endpoints will not use the
X.509 certificate and the private key we just created. Luckily, cert-manager can create
key pairs and certificates for ingress paths for us. The script described in appendix B
already ensures that cert-manager can do this, we need to configure our microservice to
```

2. Cryptography IV Software Security for Autonomous Systems

```
enable HTTPS for our ingress and ensure that the ingress controller uses HTTPS to talk
to our intern microservice endpoints since in listing 2.3 line 12 we disabled all insecure
requests^13 Listing 2.6 shows the properties needed for configuring the ingress and force
certificate generation.
```

```
Listing 2.6: Configure HTTPS for ingress paths
```

1 qua rku s. kube rne t e s. i ng r e s s. ho st=qua rkus−examp l e. t e s t
2 qua rku s. kube rne t e s. i ng r e s s. t l s. he l lo−s e rv i ce−t l s−s e c r e t. enab l ed=t rue
3 qua rku s. kube rne t e s. i ng r e s s. t l s. he l lo−s e rv i ce−t l s−s e c r e t. ho s ts=${qua rku s. kub
e rne t e s. i ng r e s s. ho s t}
4 qua rku s. kube rne t e s. anno t a t i on s. " c e rt−manage r. i o/ i s sue r "=iv−s s as−examp l e−ca−
i s sue r
5 qua rku s. kube rne t e s. anno t a t i on s. "ng i n x. o r g/ s sl−s e rv i c e s "=${qua rku s. kube rne t e
s. name}
6 qua rku s. kube rne t e s. anno t a t i on s. "ng i n x. i ng r e s s. kube rne t e s. i o/backend−pr o t oco
l "=HTTPS
7 qua rku s. kube rne t e s. anno t a t i on s. " c e rt−manage r. i o/common−name "=he l lo−t l s− i ng r
e s s
8 qua rku s. kube rne t e s. i ng r e s s. t a r get−po rt=ht tps
9 qua rku s. kube rne t e s. po r t s. ht tp s. t l s=t rue

```
In line 1, we define a hostname that should be used for the ingress rule. This may be
something you need to change if you cannot configure the mapping of *.test domains
to localhost^14. In line 3, we need this hostname again since it needs to become part of
the certificate we request. In line 2, we enable the ingress for our microservice. Line 4 is
responsible for selecting the CA that shall issue the certificate, which is the same CA as
our backend. Line 5 identifies the Kubernetes service name that shall be used. In line
6, we enforce the use of HTTPS when talking to this service. In line 7, we configure a
common name for the certificate. Line 9 enables HTTPS, and line 8 sets HTTPS as the
default target port. Listing 2.7 shows the resulting certificate chain.
```

```
Listing 2.7: The certificate chain for microservice ingress
```

1 Ce r t i f i ca t e :
2 Da t a:
3 Ve r s i o n: 3 (0 x2)
4 Se r i a l Numbe r :
5 f 1 : d c:3 d: b9:6 e : f 7 : d0:6 c : e d: a0: c0:4 e : 7 9 : d8: b6: c9
6 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
7 I s sue r : O=TU Be r l i n , OU=AOT, IV SSAS , CN=IV SSAS CA

(^13) Here one issue with Quarkus and dependencies used by it needs to be mentioned, they work very well
for HTTP/insecure requests, but not always very well for enforced HTTPS connections. Liveness
and readiness probes could fail if not configured correctly for HTTP, and problematic is the metric
data collection since the automatic creation of the ServiceMonitor resource does not support TLS
and needs to be disabled since it causes errors when encountering manual TLS configuration for such
a resource. I suggest you do not use readiness and liveness probes and metric data collection for
homework assignments.
(^14) I used this guide https://gist.github.com/ogrrd/5831371 to set-up dnsmasq on macOS. You may
have issues with this approach on Windows since localhost in WSL is not reachable from Windows.
This may require additional configuration in the network settings. So, it could be easier to change
this to a hostname or IP address that works for WSL and Windows on your machine.

2. Cryptography IV Software Security for Autonomous Systems

8 Va l i d i ty
9 No t Be f o r e : Feb 18 09:18:29 2025 GMT
10 No t Af t e r : May 19 09:18:29 2025 GMT
11 Sub j e c t : CN=he l lo−t l s− i ng r e s s
12 Sub j e c t Pub l i c Key In f o:
13 Pub l i c Key Al go r i thm: id−e cPub l i cKey
14 Pub l i c−Ke y: (256 b i t)
15 pub:
16 04:88:5 d : 2 1 : 1 9 : f 3 : 6 2 : 5 c : 3 4 : b0:8 f : f 2 : e7: f 5 : e b:
17 8 c : f 4 : 3 8 : 0 f : 6 2 : 7 3 : 3 5 : b2:8 c : 4 5 : 4 9 : e7:3 b:5 b:9 b:
18 97: a d:82:3 e : b c : a c:1 f : 1 b:59: d9:5 a: b7:62:5 f : 2 2 :
19 2 d:20: e8: f d:0 d : 1 1 : 0 5 : e 5 : 8 0 : 7 5 : b b:46: b0: e c : 3 6 :
20 e 1 : 4 4 : 6 7 : d6:22
21 ASN1 OI D: pr ime256v1
22 NIST CURVE: P− 256
23 X509v3 ext ens i on s :
24 X509v3 Key Us ag e : c r i t i ca l
25 Di g i t a l S i gna tur e , Key Enc i phe rment
26 X509v3 Ba s i c Cons t r a i nt s : c r i t i ca l
27 CA:FALSE
28 X509v3 Autho r i ty Key Ident i f i e r :
29 29:DB:D4:4 B:60:4 E:98:AE: A9:0B:B5: C0: F9:5 B:6 F:BC:9 A:05:5 C:1A
30 X509v3 Sub j e c t Al t e rna t i ve Name :
31 DNS: qua rkus−examp l e. t e s t
32 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
33 S i gna tur e Va l u e :
34 3 0 : 4 5 : 0 2 : 2 0 : 5 6 : a c : a6: d8:49: c5: e e : 4 1 : a b : 6 7 : 6 4 : a1:8 a:2 f :
35 75: f f : 3 5 : 6 e:4 f : 1 4 : bb:6 b: a a: c1: b7:02:4 c : 2 6 : 8 d:2 b : 5 2 : 2 6 :
36 0 2 : 2 1 : 0 0 : 8 c : c b: b 5 : 3 3 : 4 3 : a a: d6:68:6 d:07: c4: e d:12: e8: c1:
37 c d : 5 4 : 5 6 : 9 0 : c e:7 f : c5: b6:db: d0: e c:1 f : 4 5 : 2 0 : c6: b1:7 e
38
39 Ce r t i f i ca t e :
40 Da t a:
41 Ve r s i o n: 3 (0 x2)
42 Se r i a l Numbe r :
43 4 c : 5 6 : 5 3 : 2 a:2 a:0 d:57:6 a: c2: c b:01: f 5 : b4:3 e:7 b:5 e
44 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
45 I s sue r : O=TU Be r l i n , OU=AOT, CN=De f au l t CA IV SSAS Examp l e Cl us t e r
46 Va l i d i ty
47 No t Be f o r e : Feb 18 09:13:31 2025 GMT
48 No t Af t e r : May 19 09:13:31 2025 GMT
49 Sub j e c t : O=TU Be r l i n , OU=AOT, IV SSAS , CN=IV SSAS CA
50 Sub j e c t Pub l i c Key In f o:
51 Pub l i c Key Al go r i thm: id−e cPub l i cKey
52 Pub l i c−Ke y: (256 b i t)
53 pub:
54 04:2 b:56: e b:4 e:2 c : 0 3 : 4 e:3 e:1 f : 0 f : a8:97:6 d:10:
55 7 d:20: d1:1 d:0 d: c2:0 e : a b:b a:2 a:5 a : 1 2 : 7 0 : e f : f 1 :
56 e f : 6 c : 1 3 : 7 3 : e7:97: 4 d:2 f : 6 5 : e9:1 e : 0 1 : d a : 7 0 : c5:
57 72: a e : 7 8 : 9 d: a0:8 e : c5: f 3 : c d:48: c d: f 2 : d 4 : 5 3 : 5 3 :
58 7 d: f 8 : c 2 : 3 0 : d5
59 ASN1 OI D: pr ime256v1
60 NIST CURVE: P− 256
61 X509v3 ext ens i on s :

2. Cryptography IV Software Security for Autonomous Systems

62 X509v3 Key Us ag e : c r i t i ca l
63 Di g i t a l S i gna tur e , Key Enc i phe rmen t , Ce r t i f i ca t e S i gn
64 X509v3 Ba s i c Cons t r a i nt s : c r i t i ca l
65 CA:TRUE
66 X509v3 Sub j e c t Key Ident i f i e r :
67 29:DB:D4:4 B:60:4 E:98:AE: A9:0B:B5: C0: F9:5 B:6 F:BC:9 A:05:5 C:1A
68 X509v3 Autho r i ty Key Ident i f i e r :
69 1D:9E:4 D:5B:BD:86:93:40:63:7 B:3D:E0: E6:7 A:4D:F0: A6:37: D4:B1
70 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
71 S i gna tur e Va l u e :
72 3 0 : 4 5 : 0 2 : 2 0 : 3 c : 0 8 : 7 e : e0:6 d : 0 3 : 6 0 : b 8 : 9 4 : 6 9 : b0: c3: c a: c6:
73 48:2 b: f 8 : 8 9 : b4:79: a c : f 2 : 8 1 : 9 1 : 9 c : b3: d 3 : 0 7 : 2 1 : 1 6 : 8 4 : b a:
74 0 2 : 2 1 : 0 0 : d9: e a:9 b : 3 9 : 1 9 : 9 b : 9 7 : 6 4 : 9 4 : c d: a0:6 b:44:5 c:4 c :
75 79: f 3 : e1: f 4 : 2 7 : 4 b:0 f : 4 f : a7: c e : 5 4 : 1 0 : db:4 d: a7:24: d8

```
In line 31, you can see the most relevant difference to the microservice backend cer-
tificate in listing 2.5. Here, the subject alternative name is not a Kubernetes internal
name but the hostname we defined for the ingress rule^15.
This was our last step in configuring TLS for a microservice and enforcing TLS for
cluster internal communication and externally exposed endpoints. Since we soon talk,
in chapter 4, about security requirements, you need to consider when TLS is required,
where it is needed (inside the cluster, only for ingress paths), if HTTP and HTTPS
should be allowed, and if redirects should be allowed. These decisions should have
reasons and usually have arguments for and against them. Cryptography only provides
the tools for TLS, but why it is needed is still a decision that needs to be made. When
start working on requirements, we need to keep one caveat in mind: Important: Every
resource and user with read access to secrets in the example namespace can read the
private key stored in a Kubernetes secret. The key is stored in clear text without any
protection. There are more secure approaches for this, which we do not cover in the
lecture but which you should look into for production scenarios.
```

### 2.9. Conclusion

```
Last week, we discussed security goals; two of them were confidentiality and integrity.
This week, we looked at cryptographic methods that help us achieve them, and we saw
how we could apply them to the TLS protocol and how TLS can be used with Quarkus
in a Kubernetes cluster. To summarize:
```

- We use asymmetric and symmetric encryption in the TLS protocol.
- We use digital signatures for X.509 certificates, which we need for TLS.
- Pseudo-random numbers are needed to generate cryptographic keys.

(^15) if you closely inspect, you will also note other differences, mainly for the key usage; these differences
are primarily due to default settings, and that there was no attempt on my side to configure them
on a consistent manner.

2. Cryptography IV Software Security for Autonomous Systems

We still need to discuss in detail how we identify our security goals and what role the
technologies we use may play in achieving them.

## 3. Kubernetes and Microservices

So far, we have addressed general security terms and goals and discussed how cryptog-
raphy can be used to achieve these goals. As mentioned, we will focus on microservices
since they share some properties with intelligent agents.

### 3.1. Microservices

In traditional software development, developers typically created a single, monolithic
application where everything depended on everything else. This was _monolith_ was some-
times “lovingly” called a _big ball of mud_.

#### 3.1.1. What are microservices?

The core idea behind microservices is that you no longer develop huge applications but
small, focused ones.

```
Monolith
```

```
Module A Module B
```

```
Module C Module D Module E
```

```
Database
```

```
Microservice A
```

```
Database
```

```
Microservice B Microservice C
```

```
Database Database
a) b) c)
```

Figure 3.1.: A typical monolithic application contains all functionality, see a). For b)
the big application was split up into several modules that interact with
each other to form the overall application. In c), several self-contained
components/processes only interact through communication interfaces. The
figure is inspired by [30]
.

These small, concentrated applications are self-contained and only interact through
well-defined interfaces with other applications. These applications no longer perform
hundreds of tasks; instead, they do one thing exceptionally well. Since they are small,

##### 34

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

do only one thing, and are usually distributed, they are called _microservices_. This is
a property they share with intelligent agents, which are also supposed to excel in one
area. The main difference is that developers implement how this one thing should be
done in a microservice. Intelligent agents set goals and are able to create plans to
achieve these goals, either alone or in cooperation with other intelligent agents. Another
shared property is the distribution and communication between agents and between
microservices. We develop distributed systems in both cases, and the only differences are
in the communication technologies, protocols, and standards. For an in-depth discussion
on microservices, what they are, and how to build microservice-based applications, see
[30].
Our approach will be simpler than the general idea of microservices. In the lecture,
the following properties will be true for all microservices:

- We will use Java and Quarkus to develop a microservice.
- We will use REST for communication between microservices.
- Our microservices will be deployed to a Kubernetes cluster.

The second point on the list also hints that not everything is perfect in the world of
microservices. Individually, they are much simpler than a monolith and, therefore, easier
to develop and maintain. However, to be useful to users, you need to combine multiple
microservices and orchestrate them.

**There ain’t no such thing as a free lunch**

Recently. Some companies/development teams move away from microservices, since that
approach did not fulfill the expectations they had. Microservices enable small, focused
development, but the actual application remains the same, and with it, the complexity
of it. Microservices also introduce additional issues that need to be addressed. Possible
additions are:

- Need for a distributed system
- Network communication and related issues (failures, latency,_..._ )
- Deployment orchestration
- Team coordination
- Security -_..._

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

#### 3.1.2. Microservices and security

If we have a distributed system, we also need to consider security, as we cannot assume
that communication between microservices will not be compromised and that other
microservices can be trusted. In the lecture, we will focus on the following security
aspects:

- Securing communication between two microservices.
- Limiting access to functionality provided by a microservice.
- Managing secrets for a microservice.

Securing communication between microservices is already addressed in Chapter 2.7.
However, we must consider a few additional factors beyond simply enabling TLS. An
incomplete list of other relevant considerations:

- Do we require mutual TLS, or is just the server required to present a certificate?
- Do we need to enforce a limit on cipher suites that can be used for TLS?
- Do we allow HTTP and HTTPS for endpoints? If not, do we redirect HTTP traffic
  to the HTTPS endpoint?
- Do we require additional encryption or integrity mechanisms for the payload, or is
  TLS sufficient?

As with securing the communication, we also have to make decisions about limiting
access to microservices. Basic decisions include:

- Do we limit access to microservices based on usernames and passwords, roles, or
  API tokens?
- Who is managing usernames, passwords, and roles?
- Are there standards we need to support, e.g., for supporting single sign-on?
- Do we require rate limitations in requests?
- Is access control to a microservice enough? Or do we also require some form of
  network separation?

When it comes to security, we also have to manage secrets. One example is the
management of private keys for asymmetric cryptographic approaches. Again, this is an
incomplete list of secrets and what to do with them:

- Private keys and how to store and delete them. This also includes decisions about
  access rights to folders and files and how they could be deleted securely.

3. Kubernetes and Microservices IV Software Security for Autonomous Systems
   - Where are passwords stored, how to handle configuration files with passwords,
     and, for example, in combination with version control systems?
   - How does managing secrets differ when you are not using them, but your applica-
     tion is responsible for creating, storing, and distributing them?
   - Who is allowed to receive usernames and passwords? How do you deal with secrets
     when your microservice is only a broker between a client and another microservice
     that requires user authentication?

And to make matters worse, all the questions and decisions depend on technologies
for developing, deploying, and operating microservices.
While the lecture has mostly a theoretical view on secure software development, we
will use many technologies in the homework tasks and the example. In this chapter, we
will present an overview of some of those technologies.

### 3.2. Overview of used technologies

When developing microservices, we will depend on a few infrastructure services. These
services are mainly required to securely operate microservices.

**Technologies (potentially) used**

```
[Software System] Consul
Service Mesh, Service DiscoveryKey Value Store, API Gateway,
```

```
[Software System] Vault
Secret Storage, PKI
```

```
cert-manager [Software System]
Injects certificates into pods
```

```
[Software System] Keycloak
User & role management, token generation,OIDC, OAuth 2.0
```

```
Active Directory [Software System]
User management
```

```
[Software System] Open LDAP
User management
```

```
[Software System] k9s
Manage K8s cluster
```

```
[Software System] Helm 3
Kubernetes packet manager
```

```
kind [Kubernetes Cluster]
```

```
[Software System] kubectl
Manage K8s cluster
```

```
[Container: Quarkus] Microservice
Description of microservice typecontainer role/responsibility.
Microservice Pod [Kubernetes Pod]
```

```
use in K8s login [optional]
```

```
Used for RBAC
```

```
connects to [optional]
```

```
connects to [optional]
```

```
Provide/renew X.509 certificates
```

```
Use as PKI
use as storage backend
```

```
use for service discovery
```

```
[Software System] Quarkus
Microservice framework, used together withMaven for development and deployment.
```

```
deploy microservice
```

Figure 3.2.: Kubernetes is the typical deployment target for microservices. When
Quarkus and Kubernetes are used together, common tools and technologies
play a role in securely developing, deploying, and operating microservices.

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

Figure 3.2 presents a high-level picture of what we (could) use during the tutorial. The
main purpose of these shown technologies is to provide secure communication, access
control, and manage secrets.
Underlying all these technologies will be Kubernetes^1. This will be the target envi-
ronment where we will deploy our microservices too.

### 3.3. Kubernetes

Before we talk about Kubernetes, we first have to take a look at containers and Docker^2.
In section 1.1.3, we briefly discussed how a monolithic application differs from a mi-
croservices approach. One aspect not mentioned was that microservices are typically
deployed and executed as containers. A typical option for this is Docker.

```
Important for homework tasks
```

```
For homework assignments, it will be required that you work with Docker. So
ensure you have a working Docker version installed and know the basics of how
to use/work with Docker.
```

Figure 3.3 shows the typical workflow when working with Docker. A developer needs
to create a description of the microservices that should be deployed^3. Docker will use
this description to create a Docker _image_ , which will be stored in an _image registry_. This
registry can be a local registry, a private registry for a company, or a public one, such
as Docker Hub. When the microservice shall be executed, Docker pulls the image from
the registry and creates a _container_ from the image, which contains a runtime instance
of the image.

Figure 3.3.: Typical workflow for developers when working with Docker. Image
source:[25]

(^1) https://kubernetes.io/
(^2) https://www.docker.com/
(^3) Later, we will see how this is eased by using Quarkus

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

One question that may remain is, “Why do we execute microservices as containers
and not as a regular application?”. With regular applications, we may run into version
conflicts from dependencies, and we share the same environment with all applications.
Containers promise isolation between applications and are less resource intensive than
running applications on separate virtual machines; see figure 3.4.

Figure 3.4.: Comparison of applications running in containers and separate virtual ma-
chines. Image source: [25]

```
Further reading
```

```
The discussion of containers and container separation here is very simplified. If
you are interested in more details about this, I suggest starting by reading the
chapter of [25] (or the hopefully soon-appearing second edition). A more in-depth
discussion about container security can be found in [31].
```

With Docker, we can run containers on a single host, which is nice but not perfect
if we have many microservices and need load balancing or redundancy if a microservice
fails. With Kubernetes, we get container orchestration that addresses these (and more)
issues and requirements.
Typically, a Kubernetes cluster consists of multiple nodes. In Kubernetes, we can
distinguish between the control plane and the worker nodes. In the control plane, we
have master nodes that provide an API to interact with the cluster and manage the
worker nodes and the workload executed on them. The control plane also contains an
etcd database used to store Kubernetes-related data, state information, and secrets.
etcd nodes are either part of the master nodes or can be run separately. Master nodes
(and etcd) should always exist in odd numbers so that voting/coordination protocols
can select leaders, and the cluster has some redundancy if one node fails.
See figure 3.5 for a simplified overview of the Kubernetes cluster. Finally, having

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

```
Figure 3.5.: Control plane and worker nodes in a Kubernetes cluster. Image source:[25]
```

multiple worker nodes aids in load balancing and allows replication for microservices.
While ideally, having multiple master and worker nodes is best, it is also possible to run
a complete Kubernetes cluster as a single node. Typically you use _minikube_ or _kind_ for
this. In the tutorial, we will use kind^4.
In figure 3.6, you can see how you, as a developer, use containers and Kubernetes
together. We again have a description of the application we want to deploy. This time
the description may result in multiple containers being executed. It also allows us to
specify the number of required replicas for a container.

Figure 3.6.: Developer deploys and runs a microservice in Kubernetes. Image source:[25]

#### 3.3.1. Kubernetes components

A Kubernetes cluster is typically a distributed system comprising many cooperating
components. For these components, we have two groups: the _control plane_ for managing
the cluster and workloads, and a _node_ for executing the workload. In figure 3.7, we see
those core components of a Kubernetes cluster.
In the lecture, we will not look at the details of the components. Specifically, we
will not focus on securing the cluster itself. Since this could be its own lecture. For

(^4) Earlier versions of the lecture used minikube. So there may be outdated text passages referring to it.

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

Figure 3.7.: Components in a Kubernetes cluster. Source: https://kubernetes.io/
docs/concepts/overview/components/

the lecture, it is sufficient to understand that we interact with the _kupe-apiserver_ either
through the kubectl command-line tool or Quarkus Maven plugins.
_etcd_ is also worth mentioning, as it is the key-value store behind Kubernetes secrets
and config maps.

#### 3.3.2. Kubernetes nodes

We already discussed the control plane and the workload separation in Kubernetes.
In practice, the control plane runs one or more dedicated nodes (virtual machine or
bare metal). Workloads are executed on worker nodes. For the control plane nodes,
it is essential to always have an odd number of nodes, which is necessary for majority
decisions among nodes.
In the lecture, we use a simple Kubernetes cluster that only has one node. This node
contains the workload and the control plane^56 A more typical cluster set-up involves
multiple nodes, see figure 3.8 for an example.
In this case, etcd is running alongside other control plane components. This deploy-
ment is easier, but also less resilient since etcd or control plane failures affect each other.
Another security-related issue is the use of encryption keys. In the case that etcd and the
control plane share a node, it is theoretically possible that a compromised component
on the node gains access to keys used by etcd. The downside of distributing etcd and
the control plane is that it requires twice as many hosts for the nodes. Like the control
plane, etcd also needs an odd number of nodes.

(^5) This will only affect load balancing and self-healing capabilities of the cluster. Otherwise, the cluster
will behave like a regular cluster.
(^6) If you like to experiment and you have too much time on your hands: You can modify the start cluster
script and launch a cluster with multiple nodes.

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

Figure 3.8.: Options for Highly Available Topology - Stacked etcd topology. Source:
https://kubernetes.io/docs/setup/production-environment/tools/
kubeadm/ha-topology/

#### 3.3.3. Ingress

So far, we have discussed the standard Kubernetes components and deployment topolo-
gies, but we have a small problem: we will not be able to communicate with any service
running inside the Kubernetes cluster.

```
cluster
```

```
Ingress-managed
load balancer Ingress routing rule
```

```
Pod
```

```
Service
```

```
Pod
```

```
client
```

Figure 3.9.: Ingress - Talking to your application. Source: https://kubernetes.io/
docs/concepts/services-networking/ingress/

You have different options for enabling communication between a pod running in a
Kubernetes cluster and the outside world, but we will only consider the _ingress_ option.
For this, we need an _ingress controller_ , which needs to be configured and deployed to
Kubernetes. Again, various options exist; in the lecture, we will use nginx.
For deploying our application this means we need to specify which containers belong
to the _pod_ of an application, how many replicas of the pod should exist, what the _service_
will be, that distributes requests between pods, and the _ingress rule_ that tells the ingress

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

controller which requests to route to our service.
For the lecture, you will also have to make important decisions related to ingress, TLS,
and certificates. You need to decide if TLS connections should terminate at the ingress
or at the service/pod. For the latter case, it is needed to enable TLS passthrough at
the ingress controller^7. Another consideration is whether you believe it is sufficient for
a TLS connection between the client and ingress, and whether communication between
an ingress controller or any other pod in the cluster and a service should require TLS or
not. A consequence of these decisions is that you need to generate X.509 certificates for
the service and for the ingress to your service. Additionally, you should be aware that
your X.509 certificates will require domain/hostnames as alternative names. This means
that, in one case, you need the internal (fully qualified) hostname, and in the other case,
the fully qualified name for the service.

### 3.4. Quarkus

Typically, deploying applications to Kubernetes involves creating YAML configuration
files, which can be prone to errors. Quarkus promises, among other things, to ease the
deployment of Java applications to Kubernetes. Quarkus^8 is a framework that has as a
primary goal to support the Kubernetes-native development of microservices (or author
applications to be deployed to Kubernetes.)
Quarkus promises:

- “Developer joy”
- “Kubernetes-native”
- “Best of Breed Libraries and Standards”
- “Imperative and reactive code”

For the lecture, the relevant aspect in this list is _Kubernetes-native_. Quarkus provides
the following aides in this regard.

- **“Single-step Deployments”** Quarkus provides plugins that automatically de-
  ploy a microservice to a Kubernetes cluster. A developer does not need to create
  the typical YAML files for this. This step is done by Quarkus and can be config-
  ured to specific needs beyond default settings. For homework tasks, the defaults
  usually should work.
- **“Tracing & Debugging”** This is a useful and helpful feature, but there are
  currently some inconsistent descriptions/competing approaches for this. Therefore,
  we ignore this in the lecture.

(^7) Since you are going to use scripts for starting the cluster, this decision is made for you and TLS
passthrough is disabled.
(^8) https://quarkus.io

3. Kubernetes and Microservices IV Software Security for Autonomous Systems
   - **“Application Health & Metrics”** In a microservice environment, it is useful to
     know the health status of microservices since this may impact the performance or
     availability of customer-facing services. Although an important issue, we will not
     use this feature in the lecture.
   - **“Remote Development”** Allows you live code in a Kubernetes environment.
     Since this means you can code your application in your production environment,
     it is also a development not encouraged for secure software development.

#### 3.4.1. Service discovery

We have one main issue with microservice deployment: How do we find other microser-
vices? IP addresses are dynamically assigned in the Kubernetes cluster.
Service discovery is a mechanism to find other services. Typically, this is done us-
ing names or, in more complex cases, service-related meta-information (version, pre-
condiction,... ). Unfortunately, service discovery in Quarkus is not well supported, but
we have a few options:

- Use the service discovery in Kubernetes to find other services.
- Use Consul as a service discovery.^9
  The benefit of this approach is it also works without Kubernetes.
- Service discovery is done through configuration and does not impact the REST
  API interfaces or service logic.

Everything is done for deploying and using microservices in Kubernetes. Unfortu-
nately, we are not quite finished since everything we have done so far is insecure.

#### 3.4.2. Secret storage

When deploying microservices, we must deal with secrets and need a better way to have
them as plain texts in configuration files or the source code. The following are a few
examples of secrets we have to deal with:

- Passwords
- Private/secret cryptographic keys
- Access tokens
  -...

```
With Vault^10 , Quarkus provides an option for storing secrets.
```

(^9) This what we will focus on. I did not get the Kubernetes discovery approach to work for the develop-
ment mode.
(^10) https://www.vaultproject.io/

3. Kubernetes and Microservices IV Software Security for Autonomous Systems

**X.509 certificates**

One type of secret that we need to store is the private key for an X.509 certificate and
the password to access the private key. What are they, and why do we need them?

- Secure communication with TLS uses asymmetric encryption and therefore needs
  certificates for the involved public keys.
- A X.509 certificate binds information to a public key.
- In our cases, for example, a microservice’s identity (URL).
- X.509 certificates can be generated manually or with cert-manager and Vault in
  Kubernetes.

With TLS, we ensure secure communication between two parties, but we still need to
decide who can use our microservices.

#### 3.4.3. Access control

Access control and microservices are an interesting issue since we would need to distin-
guish between microservices allowed to talk to another microservice and the users on
whose behalf two (or more) microservices talk to each other. The lecture will only look
at a simplified version of this problem. For this, we will discuss the following topics later
in more detail:

- Role-based access control (RBAC)
- OpenID Connect (OIDC) and OAuth 2.0
- Keycloak

## 4. Software requirements

```
Goals
```

- Understand software requirements and why it is important to identify them.
- Gain an understanding of how security requirements are identified and used
  in later stages of the software development life-cycle.
- Develop an awareness about conflicting goals behind requirements and need
  to decide which requirements need to be prioritized and what consequences
  these decisions will have.

In this lecture, we assume a basic familiarity with software engineering principles.
A good general introduction is [42]. If you want a broad overview of the Software
Engineering Body of Knowledge, check out [6]. For an in-depth discussion of software
requirements, look at [53].

### 4.1. Introduction to requirements engineering

```
A software requirement can be defined as follows:
```

```
“At its most basic, a software requirement is a property that must be
exhibited by something in order to solve some problem in the real world.” [6]
```

```
Another a little bit more detailed definition is:
```

```
“ Requirements are a specification of what should be implemented. They
are descriptions of how the system should behave, or of a system property
or attribute. They may be a constraint on the development process of the
system.” taken from [53], originally from [43]
```

At the beginning of a development project, it is important to identify the require-
ments for the product. Without this, it is difficult to decide on priorities and tasks
and to identify when the product is finished. Two aspects are important for identifying
requirements: They need to be documented, and it should be possible to verify if they
have been fulfilled, e.g., through manual or automated tests. A challenge in this regard
is if requirements gathering is considered as a phase that precedes other phases in the
SDLC. High-level requirements can be identified before any design or implementation
occurs, but more specific security requirements need a system design and depend on

##### 46

4. Software requirements IV Software Security for Autonomous Systems

technology decisions. For example, considering cross-site scripting (XSS) only needs to
be addressed if we develop a web-based application^1. This means you should revise
existing requirements and add additional requirements after you detail your design or
start a new development iteration.
There are many types of requirements; see table 4.1 for an overview [53].

```
Table 4.1.: Types of requirements, taken from [53]
```

```
Term Definition
Business requirement A high-level business objective of the
organization that builds a product or of a
customer who procures it.
Business rule A policy, guideline, standard, or regulation
that defines or constrains some aspect of the
business. Not a software requirement in itself
but the origin of several types of software
requirements.
Constraint A restriction is imposed on the choices
available to the developer for the design and
construction of a product.
External interface requirement A description of a connection between a
software system and a user, another software
system, or a hardware device.
Feature One or more logically related system
capabilities that provide value to a user and are
described by a set of functional requirements.
Functional requirement A description of behavior that a system will
exhibit under specific conditions.
Nonfunctional requirement A description of a property or characteristic
that a system must exhibit or a constraint that
it must respect.
Quality attribute A kind of nonfunctional requirement that
describes a service or performance
characteristic of a product.
System requirement A top-level requirement for a product that
contains multiple subsystems, which could be
all software or software and hardware.
User requirement A goal or task that specific classes of users
must be able to perform with a system or a
desired product attribute.
```

(^1) If you are interested in what XSS is, you could start with https://owasp.org/www-community/
attacks/xss/.

4. Software requirements IV Software Security for Autonomous Systems

Figure 4.4 shows what information sources can be used to find requirements, which
influence their definition, and what could be deliverables for a requirement phase. These
requirements documents should be considered living documents that change over the
lifetime of a project. In simple cases, it is something that is changed on a whiteboard,
or in more complex cases; there is a formal change process that involves multiple project
stakeholders.

```
RequirementsBusiness
```

```
RequirementsUser
```

```
RequirementsSystem RequirementsFunctional
```

```
Business Rules
```

```
Attributes Quality
```

```
InterfacesExternal
Constraints
```

```
Software Requirements Specification
```

```
Vision and Scope Document
```

```
User Requirements Document
```

Figure 4.4.: “Relationships among several types of requirements information. Solid ar-
rows mean ‘are stored in’; dotted arrows mean ‘are the origin of’ or ‘influ-
ence’.” [53].

### 4.2. Security requirements

For us, software requirements are not the central focus. We are interested in identifying,
collecting, and describing security requirements. One important aspect here is while
requirements typically describe what a product should do, with security requirements,
we focus on cases that should not be possible. For example, we want to identify who
should be unable to identify sensitive data.
When discussing security requirements, we must also consider security-related require-
ments that concern themselves with _privacy_ and _safety_. Privacy requirements may con-
flict with security requirements; for security purposes can be helpful to collect a lot of
personal information and create a behavior profile; the goal would be to detect behavior
deviations and link them to an attack. Privacy requirements, on the other hand, may
demand limiting the collection of personal data. During the requirements-gathering
process, this conflict needs to be resolved, either by giving higher priority to one set
of requirements or adding additional requirements to find a compromise between them.
Safety requirements may, for example, be a source for additional security requirements.
In the case of autonomous cars, safety requirements may deal if handling error cases
or restricting driving functionality. Security requirements can support this with access

4. Software requirements IV Software Security for Autonomous Systems

control or data validation requirements.
Another important aspect of finding (security) requirements is that it is not only up
to the stakeholders and the engineers working on a product to find requirements. Many
outside sources already provide requirements for a product; some example sources are:

- Laws & regulations
- Contractual obligations
- Business rules
- Company security policy
- Known security vulnerabilities
- Known security weaknesses
- Known attack patterns
  -...

As you can see in figure 4.5, the terms we introduced in the first lecture also play
an important role in identifying security requirements. We have one or more _assets_
we need to protect. These may be harmed by an _adversary_ /attacker during an _attack_.
This attack may exploit a _vulnerability_ caused by a _weakness_. The potential of such
an attack is the _threat_ we need to identify. The _risk_ associated with the threat is
something we have to address with a _security requirement_. The collection of requirements
represents our _security policy_ which should help us meet our _security goals._ Having
security requirements is also needed for identifying the _security mechanisms_ we need to
integrate into our design and later implement.
The important part of the previous paragraph is that we need a clear understanding
of what our security goals are concerning our assets and what the value of these assets
is. To identify assets and threats, various sources of information can be helpful to us.

#### 4.2.1. Sources of information

Sources of information that help us identify security requirements can be either of a
technical or regulatory/legal nature.

**Regulatory and legal sources**

In the lecture, we will focus on the technical information source, but some examples of
non-technical information sources are:

- Sarbanes-Oxley Act (SOX) “The Sarbanes–Oxley Act of 2002 (Pub.L. 107–204, 116
  Stat. 745, enacted July 30, 2002), also known as the ’Public Company Accounting
  Reform and Investor Protection Act’ (in the Senate) and ’Corporate and Auditing

4. Software requirements IV Software Security for Autonomous Systems

```
SPECIFYING REUSABLE SECURITY REQUIREMENTS
```

```
62 JOURNAL OF OBJECT TECHNOLOGY VOL. 3, NO. 1
```

```
have. Security requirements are engineered to specify the system’s security policies and
both policies and requirements should address these security risks. Security mechanisms
(e.g., user IDs, passwords, encryption, firewalls, antivirus software, intrusion detection
systems, etc.) are then architected to fulfill the security requirements. Some of these
concepts influence the engineering of security requirements (e.g., policies, risks, threats,
and assets), whereas others (e.g., security mechanisms, security vulnerabilities, and
attacks) are influenced by the security requirements.
```

```
Fig. 1: Concepts that Influence and are Influenced by Security Requirements
The following list defines these security-oriented terms that will be used during the
remainder of this column:
```

Figure 4.5.: Concepts that influence and are influenced by security requirements. Taken
from [13]

```
Accountability and Responsibility Act’ (in the House) and more commonly called
Sarbanes–Oxley, Sarbox or SOX, is a United States federal law that set new or
enhanced standards for all U.S. public company boards, management and public
accounting firms.”^2
```

- Health Insurance Privacy and Portability Act (HIPPA) “Title II of HIPAA, known
  as the Administrative Simplification (AS) provisions, requires the establishment of
  national standards for electronic health care transactions and national identifiers
  for providers, health insurance plans, and employers”^3

(^2) [http://en.wikipedia.org/wiki/SarbanesâĂŞOxley_Act](http://en.wikipedia.org/wiki/SarbanesâĂŞOxley_Act)
(^3) https://en.wikipedia.org/wiki/Health_Insurance_Portability_and_Accountability_Act

4. Software requirements IV Software Security for Autonomous Systems
   - EU General Data Protection Regulation “The General Data Protection Regulation
     (EU) 2016/679 (‘GDPR’) is a regulation in EU law on data protection and privacy
     for all individuals within the European Union (EU) and the European Economic
     Area (EEA). It also addresses the export of personal data outside the EU and
     EEA areas. The GDPR aims primarily to give control to individuals over their
     personal data and to simplify the regulatory environment for international business
     by unifying the regulation within the EU.”^4
   - California‘s Notice of Security breach

As you may have noticed from this list, these laws and regulations are bound to
countries, states in a country, or multiple countries. This means you need to consider
where your product will be used and if there are legal/regulatory conflicting requirements
in different regions of the world. With technical information sources, we, of course, do
not have to worry about this, but we will have other things to worry about.

**Technical information sources**

For identifying and formulating security requirements, it is important to understand
what kind of errors can be made during the design, implementation, deployment, and
operation of a product. Also, we need to understand how an adversary may try to attack
us and what the goals of this attack are. Luckily, we do not figure this out completely
by ourselves; there are many sources of information we can use as input.
At the most basic level, we can start with everything we, as software engineers, can do
wrong, either due to design or implementation mistakes. If we due to something wrong,
we introduce an _weakness_ in our product.

```
“... can occur in software’s architecture, design, code or implementation
that can lead to exploitable security vulnerabilities.... Software weaknesses
are flaws, faults, bugs, vulnerabilities, and other errors in software implemen-
tation, code, design, or architecture that if left unaddressed could result in
systems and networks being vulnerable to attack. Example software weak-
nesses include: buffer overflows,... ” https://cwe.mitre.org/about/faq.
html
```

Unfortunately, there are hundreds of potential weaknesses, but we also have a public,
systematic collection of them. The CWE is this collection.

```
“CWE™is a community-developed list of common software security weak-
nesses. It serves as a common language, a measuring stick for software se-
curity tools, and as a baseline for weakness identification, mitigation, and
prevention efforts.” https://cwe.mitre.org/index.html
```

```
We discussed weaknesses; now we must look at vulnerabilities.
```

(^4) https://en.wikipedia.org/wiki/General_Data_Protection_Regulation

4. Software requirements IV Software Security for Autonomous Systems

```
“A ‘vulnerability’ is a weakness in the computational logic (e.g., code)
found in software and some hardware components (e.g., firmware) that, when
exploited, results in a negative impact to confidentiality, integrity, OR avail-
ability. Mitigation of the vulnerabilities in this context typically involves
coding changes, but could also include specification changes or even speci-
fication deprecations (e.g., removal of affected protocols or functionality in
their entirety).” https://cve.mitre.org/about/terminology.html
```

```
For vulnerabilities, we also have a common collection known as CVE.
```

```
“CVE®is a list of entries—each containing an identification number, a de-
scription, and at least one public reference—for publicly known cybersecurity
vulnerabilities.” https://cve.mitre.org
```

```
One term that may need an additional definition is exposure.
```

```
“An ‘exposure’ is a system configuration issue or a mistake in software that
allows access to information or capabilities that can be used by a hacker as a
stepping-stone into a system or network.” https://cve.mitre.org/about/
terminology.html
```

Since it is, in my view, important to understand, let us again look at the difference
between a weakness and a vulnerability.
“Software weaknesses are errors that can lead to software vulnerabilities. A software
vulnerability,... , is a mistake in software that can be directly used by a hacker to gain
access to a system or network.” https://cwe.mitre.org/about/faq.html
To summarize the difference between weakness and vulnerability:

**Weakness** Something that can exist in a software product. Entries in CWE are not
related to a specific software product.

**Vulnerability** Something that exists in a specific software product. Vulnerabilities are
not contained in the CWE but in the CVE.

So far, we only look at how an adversary may use vulnerabilities in an attack. Unfortu-
nately, this is insufficient. We need a better understanding of how an adversary actually
conducts an attack. An attack typically will consist of multiple attack steps; this chain
of steps is often referred to as a cyber kill chain. Every single step or a combination of
steps can be described with an _attack pattern_.

```
“‘Attack Patterns’ are descriptions of the common elements and tech-
niques used in attacks against vulnerable cyber-enabled capabilities. At-
tack patterns define the challenges that an adversary may face and how
they go about solving it. They derive from the concept of design patterns
applied in a destructive rather than constructive context and are gener-
ated from in-depth analysis of specific real-world exploit examples.” http:
//capec.mitre.org/about/index.html
```

4. Software requirements IV Software Security for Autonomous Systems

In contrast to weaknesses and vulnerabilities, we have multiple repositories for attack
patterns. One of the best-known repositories is CAPEC.

```
“Understanding how your adversary operates is essential to effective cyber
security. CAPEC™is a comprehensive dictionary and classification taxon-
omy of known attacks that can be used by analysts, developers, testers,
and educators to advance community understanding and enhance defenses.”
http://capec.mitre.org/index.html
```

Open Worldwide Application Security Project (OWASP) also provides a collection of
attack patterns^5. But this collection is focused on attacks related to web applications.
But this is an essential source of information if you develop web applications. Besides
providing information, OWASP also provides security testing guides and penetration
testing tools.
Finally, I want to mention a last source for information that brings together the idea
of a cyber kill chain and attack patterns. ATT&CK collects tactics and techniques
commonly used by adversaries. In addition, it contains information about procedures
(why to realize a technique), adversaries, and mitigation actions.

```
“MITRE’s Adversarial Tactics, Techniques, and Common Knowledge
(ATT&CK™) is a curated knowledge base and model for cyber adversary
behavior, reflecting the various phases of an adversary’s lifecycle and the
platforms they are known to target. ATT&CK is useful for understanding
security risk against known adversary behavior, for planning security im-
provements, and verifying defenses work as expected.” https://attack.
mitre.org/wiki/Main_Page
```

The collection of tactics and techniques exists for enterprise environments, industrial
control systems, and mobile applications.
Armed with knowledge about weaknesses, vulnerabilities, attack patterns, techniques,
and tactics. We can start by thinking about security requirements, but before we begin
collecting them, we first should consider what types of security requirements we may
need to identify.

#### 4.2.2. Types of security requirements

So far, we have only discussed where we find potential information about threats that
we can use as a source for our security requirements gathering. We still need to discuss
a “formal” process for gathering them and a way to organize them.
We will start with the organization of security requirements since this will give us a
focus when we try to identify them. Unfortunately, this is not as easy as it sounds since
you will find different proposals on organizing security requirements.
In [14], you will find an extensive discussion describing security requirements. This
discussion also included numerous examples of how to describe a security requirement.

(^5) https://owasp.org/www-community/attacks/

4. Software requirements IV Software Security for Autonomous Systems

Very important in this regard is always to remember that a requirement focuses on the
**WHAT** and not the **HOW**. So, for example, you can require that a microservice client
and the microservices will use an encrypted communication channel and that they should
be able to authenticate each other. What you should not do, is to require that they use
mutual TLS (mTLS). This may, in the end, be the design/implementation solution for
this requirement, but there could be other options or requirements that prevent the use
of mTLS, for example, if you are not able to create an end-to-end transport channel
between client and microservice. If you later decide on mTLS, add additional security
requirements. For example, regarding supported TLS protocol version and cipher suites.
[14] groups security requirements into the following categories:

- Identification
- Authentication
- Authorization
- Immunity
- Integrity
- Intrusion Detection
- Nonrepudiation
- Privacy
- Security Auditing
- Survivability
- Physical Protection
- System Maintenance Security

When identifying security requirements based on this list, you should be aware that a
requirement may not stand alone but need other supporting requirements. For example,
_authorization_ will most likely be dependent on _authentication_ , which in turn depends
on _identification_. Other security requirements may conflict: _privacy_ requirements may
limit the amount of personal data you are allowed to store, _intrusion detection_ and
_security auditing requirements_ may require you to store personal information^6 Finally,
other requirements may not be relevant. _Physical protection_ requirements may not be
an issue if you do not control the physical infrastructure and deploy your application to
a public cloud provider.
If you use the OWASP Cornucopia card game^7 as a source for finding requirements
you will be confronted with a different set of types for security requirements:

(^6) Also, legal requirements may play a role here: For example, that you are not allowed to use collected
data for some purpose, e.g., tracking online activity or working hours.
(^7) https://owasp.org/www-project-cornucopia/

4. Software requirements IV Software Security for Autonomous Systems
   - Data validation & encoding
   - Authentication
   - Authorization
   - Session management
   - Cryptography
   - Cornucopia

Playing cards is a fun way of finding security requirements, but as you also can see
from the two lists: Fixating on available cards or categories alone can be a problem since
both lists have some overlap but also address security issues not included in the other
list.

#### 4.2.3. Threat Modeling

Playing cards is one way to identify security requirements, but we also have more struc-
tured threat modeling approaches; one example is STRIDE[38]. STRIDE^8 stands for:

- Spoofing
- Tampering
- Repudiation
- Information disclosure
- Denial of service
- Evaluation of privilege

We now have a few lists with possible types of security requirements and could write
them down as formal sentences. This is something we should do at some point, but still,
it could be helpful to have some aid in finding security requirements. Both the software
engineering and security community have found ways to do this.

#### 4.2.4. Misuse cases

In software development, unified modeling languange (UML) is used to model what
a software product is supposed to do. One commonly used diagram type for this are
use case diagrams. But they have one shortcoming, they only deal with what we want
a software system to do.

(^8) By the way, this also available as a card game by the name of “elevation of privilege”.

4. Software requirements IV Software Security for Autonomous Systems

```
“If the development process doesn’t address unexpected or abnormal be-
havior, then an attacker usually has plenty of raw material with which to
work.” [17]
```

To address this shortcoming, it was proposed to extend use case diagrams with security
aspects. Misuse case diagrams capture not only the expected application behavior, but
also the use cases for an adversary and how we may counter them. In the literature, we
have several definitions/descriptions of what misuse cases are:

```
“... document a priori how software should react to illegitimate use.” [17]
```

```
“A misuse case is simply a use case from the point of view of an actor
hostile to the system under design.” [1]
```

```
“A misuse case is a special kind of use case, describing behavior that the
system/entity owner does not want to occur.” [39]
```

Figure 4.6 provides an everyday example of how a person wants to drive a car, how a
thief may attempt to steal a car, and how this can be prevented.

```
Figure 4.6.: Source: [1]
```

```
As you can see in the example, we have three different types of use cases:
```

**Regualr use case** Use cases as we know them from standard UML diagrams. These use
cases are threatened by misuse cases.

**Misuse case** The adversary use cases are colored in black to have a visual distinction
from regular and security use cases.

4. Software requirements IV Software Security for Autonomous Systems

**Security use case** A security use case is linked to a regular use case and counters a
misuse case or is threatened by one.

For software systems, this can get more complex when we have to consider users,
security personnel, and different types of adversaries. Figure 4.7 provides a simplified
view of a web portal and related use, misuse, and security use cases.

```
Figure 4.7.: Source: [1]
```

An issue with figures 4.6 and 4.7 is that they use a lot of different colored lines but
have no clear visible difference for regular use cases and security use cases. In figure
4.8, we have this difference through an added label to a use case, but here we miss the
relationships between misuse cases and regular use cases.
All three, so far presented misuse case diagrams have another issue. They do not use
_stereotypes_ for use cases and connections, as is typically done in UML. They have their
own unique way of attaching labels to links and use cases. Also, no UML tool I am aware
of supports misuse cases and requires an adaptation and consistent use of identifying
misuse and security use cases. Also, one very important aspect of misuse diagrams is
that they are used to communicate to software architects, developers, stakeholders, and
other people involved in a project. That means for complex scenarios, it may not be
useful to include everything in one diagram. When your diagrams grow too large, and it
becomes difficult to follow connections between actors and use cases, or the text in the
diagram is not readable anymore without zooming into the diagram, it is maybe time to
split up the diagram. Of course, this should not be random but done meaningfully, for
example, by extracting unrelated use cases and focusing on specific actors or adversaries.

4. Software requirements IV Software Security for Autonomous Systems

```
Figure 4.8.: Source: [12]
```

You also can have standard use case diagrams and include in misuse case diagrams only
use cases from these diagrams that actually are threatened by misuse cases.

```
Figure 4.9.: Source: [12]
```

Figure 4.9 provides an overview of how misuse cases relate to security requirements.
Our assets and services are vulnerable to security threats that we identified. These
identified security threats are documented as a misuse case. A security threat/misuse
case requires the definition of security use cases to mitigate the threat. The security
use case is then a security requirement. It is important to note here that it will not
be sufficient to draw diagrams but also to formulate a textual definition for security
requirements clearly. Diagrams provide an overview and are supporting documents.

4. Software requirements IV Software Security for Autonomous Systems

The security requirements finally lead to security mechanisms we must implement to
protect our assets.
In figure 4.9, only the security team is shown, but to identify security requirements, use
cases, and misuse cases, you also will need the support of domain experts. They are the
ones who know how a system should work correctly, what legal values and interactions
are, and what an adversary may try to achieve.
You may wonder what happened to all the information sources we talked about at
the beginning of this section. This plays a bigger role in an approach from the security
community to identify security requirements.

#### 4.2.5. Attack trees

The approach of the security community to identify security requirements is more ori-
ented on how adversaries try to achieve goals. To describe this, _attack trees_ can be
used.

```
“Attack trees provide a formal, methodical way of describing the security
of systems, based on varying attacks.” [36]
```

Figure 4.10 shows a simple example of an attack tree. Here the goal for the adversary
is to open a safe, and the goal for the defender is to protect the assets. The asset could
either be the safe itself or the content of the safe. The view on this may also impact
the protection mechanisms you consider. For the attack tree opening the safe means the
adversary’s goal is achieved. As a defender, opening the safe may not be the end. This
is, for example, when the safe is not your asset but the money contained in the safe.
You could also add additional defenses for exampling exploding dye bags.

```
Figure 4.10.: Attack tree based on [36]
```

With an attack tree, you map all potential paths to the ultimate goal of an adversary.
You can indicate if more than one node below a node must be achieved to move up

4. Software requirements IV Software Security for Autonomous Systems

in the tree or if just one node is needed. This tree is then used to identify security
requirements that block paths to the goal or at least make the attack visible. Attack
patterns, vulnerarbilites, and weaknesses are potential information that can be used in
attack trees for attacks on software applications; figure 4.12 shows an example of that.

#### 4.2.6. Combining attack trees and misuse cases

Neither misuse cases nor attack trees are the right or wrong way for finding and describing
security requirements. They provide different viewpoints and detail levels. A good idea
is also to combine them.

```
Figure 4.11.: Source: [47]
```

Figure 4.11 shows a misuse case, which includes a link to an attack tree. This attack
tree is shown in figure 4.12.

```
Figure 4.12.: Source: [47]
```

4. Software requirements IV Software Security for Autonomous Systems

### 4.3. Summary

```
“Requirements should clearly explain why something must be so.” [51]
```

The sentence above perfectly summarizes the goal of the (security) requirements gath-
ering phase. Requirements are about what and why something needs to be done and not
yet about the how. Decisions on how something is to be done may lead to additional
requirements, for example, why certain cipher suites must not be used with TLS or why
older protocol versions must not be supported. Again these are _why_ explanations, the
_how_ decision was made during the design phase.
One pitfall regarding security requirements should be remarked here: Perfect security
is impossible to achieve or at least infeasible. Security requirements need to be balanced
against other requirements, e.g., usability. Also, we need to consider what is a sufficient
level of security. A bank that stores cash in a safe probably has different security demands
than me when I want to keep my Ph.D. diploma in a safe. Also, I would not be willing
to pay the price for my own bank safe. So, considering cost and available budgets also
plays a role in defining security requirements or deciding that the budget is insufficient.
A final remark on security requirements: In contrast to other requirements, security
requirements often define something that should **NOT** be possible. We started the
summary with a quote, and we will close with one:

```
“In this activity, one or more analysts will define the business-mandated
security requirements of the system. This includes requirements based on in-
dustry regulations, corporate policies, and other business-specific needs. The
analysts will be well-versed in regulatory compliance and corporate security
policies.” [45]
```

## 5. Software architectures and software design

First, we should look at why software design and architecture matter.
Before we can start implementing, we must understand what we need to implement,
what problems we need to solve, and which constraints to consider. We must also
communicate with ourselves, other team members, and stakeholders in an application.
Design helps in documenting this communication.
In the previous lecture, we discussed (security) requirements that represent the con-
straints we must consider. However, we have done this only with use case diagrams,
which may be insufficient to describe a software system and identify all relevant require-
ments. During the modeling and design of our software system(s), we need to generate an
understanding of the system’s structure and behavior within and between components.
This understanding is crucial for our primary concern _security_. Knowing the structure
lets us identify possible attack paths and points we need to secure. Knowing the desired
behavior allows us to prevent unwanted activities and implement monitoring strategies.
When we model our understanding of a problem and its solution, we will have different
views and do it on different abstraction levels. Covering all relevant information in one
diagram or textual paragraph is impossible.
Depending on the purpose of the design artifacts, standardized notions, and fine-
grained designs are required, or informal design artifacts can be sufficient. The latter
could, for example, be flip charts hand-sketched in a meeting and used as posters by
the development team to guide the implementation. The former could be hundreds of
pages in formal design documents submitted for external review and used in security
evaluations.
So, how should we model/design? An answer to this question largely depends on
what kind of project you are working on and what external requirements exist for design
documentation^1
The UML is a common approach for modeling software systems. Still, it is very
formal, complex, and not always easily accessible for domain experts with little software
engineering background. In the software community, there is a tendency to use UML
informally or with less strict notations. Chapter 5 in [42] discusses this in more detail.
The following will use a more informal alternative to UML modeling. Instead of 13
different UML diagram types, the C4 modeling approach^2 only knows for diagram types.
With the use case diagrams, we identified actors and high-level functionality; creating
a high-level overview of the structure for a software solution is also helpful. Figure 5.3

(^1) For example, what the homework task description demands.
(^2) https://c4model.com/

##### 62

5. Architectures & Design IV Software Security for Autonomous Systems

presents an overview of the research project COBRA-5G.

```
Legend Person
Software SystemContainer
ComponentExternal Person
External Software System
```

```
Administrator [Person]
Manages the COBRA-5G infrastructureand is responsible for security.
```

```
COBRA-5G MoCaR [Software System]
Monitoring (Mo), Cyberattack Simulation (Ca),and response to security problems (R)
```

```
COBRA-5G Presenter [Person]
Demonstrates the COBRA-5G features.
```

```
manage MoCaR
```

```
view results and use simulation
```

```
COBRA-5G IDS [Software System]
view results and use simulation Federated learning system for detecting attacks
Triggers attack simulations for alerts
COBRA-5G infrastructure [Hardware System]
Physical and virtual machines in the COBRA-5Gtestbeds
```

```
COBRA-5G Kubernetes cluster [Software System]
A Kubernetes cluster in the COBRA-5G project.
Gathers security events
```

```
Gathers security events
```

```
Gathers infrastructure information
```

```
Gathers infrastructure information
```

```
COBRA-5G Container registry [Software System]
Predictive container registry Pushes container images to local image registries
```

Figure 5.3.: A high-level system model for our work in the research project COBRA-
5G. Each software system represents one of our work packages/teams in the
project.

Figure 5.4 shows a system context diagram and provides more details for the network
security monitoring part.

```
Legend Person
Software SystemContainer
ComponentExternal Person
External Software System
```

```
[System Context] COBRA-5G monitoring, prediction, and response Overview for AP 5 (attack prediction and response system) in COBRA-5G
```

```
Manages the COBRA-5G infrastructure Administrator [Person]
and is responsible for security.
COBRA-5G MoCaR [Software System]
Monitoring (Mo), Cyberattack Simulation (Ca),and response to security problems (R)
```

```
COBRA-5G infrastructure [Hardware System]
Physical and virtual machines in the COBRA-5Gtestbeds
```

```
COBRA-5G Kubernetes cluster [Software System]
A Kubernetes cluster in the COBRA-5G project.
runs a
```

```
Demonstrates the COBRA-5G features. COBRA-5G Presenter [Person]
```

```
System Administrator [Person]
Operate the COBRA-5G infrastructure
```

```
manages
[Software System] Wazuh
Extended Detection and Response (XDR) andSecurity Information & Event Management(SIEM)
```

```
[Software System] Suricata
Network Intrusion Detection System
```

```
[Software System] OpenVAS
Vulnerability scan for an IT-environment.
```

```
[Software System] Zabbix
Network monitoring for an IT-environment.
```

```
security monitoring of nodes in
```

```
monitors network traffic of
```

```
scans for vulnerabilities in
```

```
monitors
```

```
[Software System] Harbor
Provides and scans containers
scans containers for vulnerabilities
```

```
manage MoCaR
```

```
view results and use simulation
```

```
get hosts and details
```

```
get discovered vulnerabilities
```

```
get security events
```

```
get security events
```

```
get vulnerabilities for containers
get cluster details
```

```
[Software System] NVD
Vulnerability database
```

```
get vulnerability and patch information
```

```
[Software System] FleetDM
Device management solution, used fordistributing Osquery
```

```
get hosts and details
```

```
get discovered vulnerabilities
```

Figure 5.4.: The system context diagram for network security monitoring shows which
external systems are needed for gathering information about an infrastruc-
ture.

While the system context diagram already provides more details, the monitoring ap-
proach is still unspecific. Figure 5.5 shows a container diagram focusing on the mi-

5. Architectures & Design IV Software Security for Autonomous Systems

croservices involved in monitoring. For simplicity, this diagram ignores alternatives to
the Osquery microservice supported in COBRA-5G.

```
COBRA-5G MoCaR (Focus Osquery) [Software System]
```

```
COBRA-5G UI [Container: Angular]
Frontend for the COBRA-5G monitoring.
```

```
Administrator [Person]
Manages the COBRA-5G infrastructureand is responsible for security.
```

```
COBRA-5G Presenter [Person]
Demonstrates the COBRA-5G features.
```

```
Interact with UI [HTTP/S]
```

```
Interact with UI [HTTP/S]
```

```
Monitoring Manager [Container: Quarkus]
Manages monitoring tasks and results
Makes [JSON/HTTP] API calls Description of microservice type[Container: Quarkus] Osquery
container role/responsibility.
```

```
Network storage [Container: MongoDB]
Description of storage type containerrole/responsibility.
```

```
Read & write topology information
```

```
Inform about new topology data [JSON/HTTP or Message bus?]
```

```
Manage Osquery monitoring [JSON/HTTP]
```

```
COBRA-5G infrastructure [External Hardware System] COBRA-5G Kubernetes cluster [External Software System]
```

```
[Software System] FleetDM
Device management solution, used fordistributing Osquery
```

```
get hosts and details
get discovered vulnerabilities
```

```
Legend Person
Software SystemContainer
ComponentExternal Person
External Software System
```

```
FleetDM agent [Software System]
Executes Osqueries
```

```
FleetDM agent [Software System]
Executes Osqueries
```

```
[Containers] COBRA-5G - Monitoring with Osquery Main source of information about an infrastructure is Osquery. Fleet DM will be used to distribute & configure Osquery.
```

Figure 5.5.: With the container diagram, we focus on all the microservices involved in
network security monitoring.

Now we know the microservices we need to implement. If we use, for example, Java
and Quarkus to implement them, we can break a microservice down into components.
Figure 5.6 shows a possible approach for the Osquery microservice.

```
Osquery (Microservice) [Container]
```

```
Monitoring Management [Component: Quarkus Java Class] Resource
Provides RESTosquery monitoring API endpoints to manage the
Monitoring Manager [Container: Quarkus]
Manages monitoring tasks and results Manage Osquery monitoring [JSON/HTTP]
```

```
Monitoring Manager [Component: Quarkus generated Client] API Client
Inform about new topology data [JSON/HTTP or Message bus?] Pushes topology information to monitoringmanager.
```

```
Osquery Monitoring [Component: PoJo]
Coordinates fetching, merging and createnetwork models with Osquery. [Component: Quarkus generated client]REST API client for fetching FleetDM entries FleetDM Client Pull scan results [JSON/HTTP]
```

```
STIX model Updater [Component: PoJo]
Creates a STIX topology model and updates it
```

```
[Software System] FleetDM
Device management solution, used fordistributing Osquery
Legend Person
Software SystemContainer
ComponentExternal Person
External Software System
```

```
[Components] Osquery Monitoring Microservice for fetching Osqeury results from FleetDM and creating network models
```

Figure 5.6.: A component diagram shows how microservices can be broken down into
smaller parts.

The last diagram in figure 5.6 could be enough to start the implementation. Still,
providing class diagrams for components can also be helpful for test-driven approaches
or if we split the development work between different developers.

5. Architectures & Design IV Software Security for Autonomous Systems

### 5.1. UML

I like to use C4, but since UML is more commonly used, you have to use it for homework
assignments, and we will discuss it in more detail.

```
UML
“Unified Modeling Language (UML) is a standardized (ISO/IEC 19501:2005),
general-purpose modeling language in software engineering. The Unified Modeling
Language includes a set of graphic notation techniques to create visual models of
object-oriented software-intensive systems.” a
a https://en.wikipedia.org/wiki/Unified_Modeling_Language
```

As mentioned in the introduction to this lecture, design, on any level, is about com-
munication with your future self, other team members, or stakeholders. It may also be
required as documentation for security evaluation or other regulatory requirements.

Figure 5.7.: Overview of UML diagram types. Source: https://commons.wikimedia.
org/wiki/File:Uml_diagram2.png

With so many diagrams, one question is, “Which diagrams must I use?” The an-
swer is that it depends on what you want to communicate. According to [42], the five
most commonly used UML diagrams are activity diagram, use case diagram, sequence
diagram, class diagram, and state diagram.
I would extend this list with a deployment diagram since it can be helpful to express
how different parts of a distributed system are deployed. For example, you could de-
scribe which pods are deployed to which namespace. You could then use deployment
diagrams to plan your Kubernetes networking policies that limit communication between
namespaces.

5. Architectures & Design IV Software Security for Autonomous Systems

### 5.2. What we want to achieve during design

We want to achieve specific engineering goals in addition to understanding a problem,
sketching a solution, communicating, and documenting both.

#### 5.2.1. Software Design Principles

- **Abstraction** “is a view of an object that focuses on the information relevant to
  a particular purpose and ignores the remainder of the information.... In the
  context of software design, two key abstraction mechanisms are parameterization
  and specification. Abstraction by parameterization abstracts from the details of
  data representations by representing the data as named parameters. Abstraction
  by specification leads to three major kinds of abstraction: procedural abstraction,
  data abstraction, and control (iteration) abstraction.” [19]
- **Coupling and Cohesion** “Coupling is defined as ‘a measure of the interdepen-
  dence among modules in a computer program,’ whereas cohesion is defined as ‘a
  measure of the strength of association of the elements within a module’... .”[19]
- **Decomposition and modularization** “Decomposing and modularizing means
  that large software is divided into a number of smaller named components having
  well-defined interfaces that describe component interactions. Usually the goal is
  to place different functionalities and responsibilities in different components.”[19]
- **Encapsulation and information hiding** “means grouping and packaging the
  internal details of an abstraction and making those details inaccessible to external
  entities.”[19]
- **Separation of interface and implementation** “Separating interface and imple-
  mentation involves defining a component by specifying a public interface (known
  to the clients) that is separate from the details of how the component is realized
  (see encapsulation and information hiding above).”[19]
- **Sufficiency, completeness, and primitiveness** “Achieving sufficiency and com-
  pleteness means ensuring that a software component captures all the important
  characteristics of an abstraction and nothing more. Primitiveness means the de-
  sign should be based on patterns that are easy to implement.”[19]
- **Separation of concerns** “A concern is an ‘area of interest with respect to a
  software design’.... A design concern is an area of design that is relevant to one
  or more of its stakeholders. Each architecture view frames one or more concerns.
  Separating concerns by views allows interested stakeholders to focus on a few things
  at a time and offers a means of managing complexity... .”[19]

Maybe not during an initial design iteration, but following iterations, we need to
consider several key issues and decide how they should be addressed in our solution. [19]
lists these following issues:

5. Architectures & Design IV Software Security for Autonomous Systems
   - Concurrency
   - Control and Handling of Events
   - Data Persistence
   - Distribution of Components
   - Error and Exception Handling and Fault Tolerance
   - Interaction and Presentation
   - Security

Thinking about these issues will impact what technologies you select for the imple-
mentation, and choosing technologies again influences design decisions and requirements.
Since these list common issues and goals, you may ask yourself if you have to address
them yourself or if you can find existing solutions. There are two options: either a
programming language or technology addresses this for you, or you can rely on proven
patterns.

#### 5.2.2. Design Pattern

We discussed patterns last week; you may recall the attack pattern catalog CAPEC. As
with attack blueprints, we also have blueprints for many recurring problems in software
development.
These common solutions are described as design patterns. One well-known pattern is
called: _Model-View-Controller (MVC)_.

```
Legend
PersonSoftware System
Container
Component
External PersonExternal Software System MVC application [Software System]
```

```
[Container: JSON, XML, Java, ...] Model
```

```
[Container: Angular View , Java, Python, ...]
```

```
[Container: Java, Python, ...] Controller
```

```
User [Person]
```

```
sees
```

```
uses
manipulates
```

```
updates
```

```
Figure 5.8.: Model View Controller (MVC) pattern
```

Figure 5.8 shows the basic structure of the MVC pattern. The idea is that a user
interacts with a controller that manipulates a data model that updates the view in a
user interface. One advantage of this approach is that all three components can evolve
independently and may also use different technologies.
[15] is the standard book on design patterns and describes the standard solutions
to commonly reoccurring problems in object-oriented software development. Another

5. Architectures & Design IV Software Security for Autonomous Systems

advantage of design patterns is that they provide a common language developers can
use to communicate with other developers. It is easier to tell some to solve a task
with the _Builder_ pattern than to describe this solution approach in detail. Figure 5.9
provides an overview of all patterns described in [15]. As you may have noticed, this
overview does not include the MVCindexdesign pattern!MVC pattern. That is because
you can and should use patterns from the catalog to realize the model, view, and con-
troller components. Useful patterns for this are _Observer_ indexdesign pattern!Observer,
_Composite_ indexdesign pattern!Composite, _Strategy_ indexdesign pattern!Strategy, _Fac-
tory Method_ indexdesign pattern!Factory Method, and _Decorator_ indexdesign pattern!Decorator.

```
Figure 5.9.: Design pattern catalogue [15]
```

Design patterns are a common solution to a reoccurring problem and are typically
described in a common format. In [15], these essential elements for design pattern
description are identified:

**Pattern Name** Describes a design problem

**Problem Description** A description of when to apply the pattern. Explain the problem
in a context.

**Solution Description** Abstract description of how to solve a design problem.

**List of Consequences** Describes results and trade-offs of applying a pattern.

**Microservic patterns**

Of course, the design patterns in figure 5.9 are not the only design patterns. There are
many books and other resources with collections for design patterns. Since we are dealing
with microservices, the patterns at https://microservices.io/patterns/index.html
interest us. This site contains a collection of patterns for solving common problem when
developing microservices, see 5.10 for an overview.

5. Architectures & Design IV Software Security for Autonomous Systems

```
Data patterns
```

```
Communication patterns
```

```
Application architecture
```

```
Cross-cutting concerns Security
```

```
Deployment
```

```
Maintaining dataconsistency
```

```
External API
```

```
Reliability
```

```
Discovery
```

```
Transactional messaging
```

```
Testing
```

```
Observability
```

```
UI
```

```
Decomposition
```

```
Database architecture
```

```
Querying
```

```
Communication style
```

```
Copyright
```

```
©
```

2024. Chris Richardson Consulting, Inc. All rights reserved. Learn-Build-Assess Microservices [http://adopt.microservices.io](http://adopt.microservices.io)

```
API gateway
Client-side discovery
```

```
Server-side discovery
```

```
Service registry
```

```
Self registration
```

```
3rd party registration
Multiple Services per host
```

```
Single Service per Host
```

```
Service-per-Container Service-per-VM
```

```
Messaging Remote ProcedureInvocation
```

```
Database per Service
Saga
```

```
databaseShared
```

```
Microservice Chassis
```

```
Backends for frontends
```

```
sourcingEvent
```

```
architectureMonolithic
```

```
Microservice architecture
```

```
MotivatingPattern SolutionPattern
Solution A Solution B
General Specific
```

```
deploymentServerless
```

```
Access Token Circuit Breaker
Domain-specific
```

```
configurationExternalized
```

```
Consumer-driven contract test
```

```
Component TestService
```

```
Exception tracking
```

```
Distributed tracing
```

```
Audit logging
Application metrics
```

```
aggregationLog
```

```
Health check API
```

```
deployment Service
platform
```

```
Server-side page fragment
composition
Client-side UI composition
business capabilityDecompose by
Decompose bysubdomain
CQRS
```

```
Transactional Transaction log tailing
Outbox Polling
publisher
```

```
CompositionAPI
```

```
Consumer-side contract test
```

```
Sidecar
Service mesh
```

```
Aggregate
```

```
Domain event
```

```
Log deployments and changes
```

```
Self-contained Service
Service per team
```

```
TemplateService
```

```
The Microservice Architecture Pattern Language
Application patterns
```

```
Infrastructure patterns
```

```
Application Infrastructure patterns
```

Figure 5.10.: Microservice patterns overview. Source: https://microservices.io/
patterns/index.html

**Design pattern and security**

Design patterns are interesting for us because they also address solutions to common
security problems we must solve. For example, see the pattern description _API Gateway_
in the boxes below.
The API gateway pattern example was taken from Micorservices.io, but we will not
discuss it in detail since our following lectures are about software and system security
patterns.
In this lecture, I will only be able to provide a brief overview of security design patterns;
for more details, you can have a look at:

- Steel et al. “Core Security Patterns: Best Practices and Strategies for J2EE _T M_ ,
  Web Services, and Identity Management” [45], see also [http://coresecuritypatterns.](http://coresecuritypatterns.)
  com/patterns.htm
- Fernandez-Buglioni. “Security Patterns in Practice: Designing Secure Architec-
  tures Using Software Patterns” [10]

5. Architectures & Design IV Software Security for Autonomous Systems
   - Schumacher et al. “Security Patterns: Integrating Security and Systems Engineer-
     ing” [37]
   - https://en.wikipedia.org/wiki/Security_pattern
   - The Open Group. Security Design Pattern. [http://www.opengroup.org/security/](http://www.opengroup.org/security/)
     gsp.htm
   - Microsoft. Security patterns. https://docs.microsoft.com/en-us/azure/architecture/
     patterns/category/security

```
API Gateway (Microservice Pattern)
```

```
Context “You have applied the Microservice architecture and API Gateway pat-
terns. The application consists of numerous services. The API gateway
is the single entry point for client requests. It authenticates requests, and
forwards them to other services, which might in turn invoke other services.”
```

```
Problem “How to communicate the identity of the requestor to the services that
handle the request?”
```

```
Forces “Services often need to verify that a user is authorized to perform an
operation.”
```

### 5.3. Summary

Although the lecture presents the SDLC as a “waterfall”, it is iterative, and the bound-
aries are not always clear. During the design, we clarify the problem we need to solve. We
use diagrams and textual descriptions to communicate our understanding of the prob-
lem and our approach to solving it. Design is also influenced by technology decisions
needed for implementation because programming languages, frameworks, and libraries
may force certain architecture decisions.
It is not possible to capture everything with one diagram or even one type of diagram.
Therefore, we use different types of diagrams. In these diagrams, we describe different
aspects of a problem and solution. Also, they may be aimed at different audiences.
During the design phase, it is also helpful to identify which design patterns are useful
for solving problems in the current software development project. Using well-proven
solutions for problems strengthens the design and may also reduce the introduction of
security issues into an application, primarily when we use software and system security
patterns. These patterns will be discussed in the following lectures.
This lecture on software design was only a very rough summary of relevant topics.
Many aspects were not addressed and should be followed up in different lectures, books,
or other resources.

## 6. Security Patterns

### 6.1. Introduction

**Security Design: “We use SSL”?**
How to meet requirements?

- Confidentiality
- Integrity
- Availability
- Privacy
  -...
  =⇒ Not sufficient to say: “We use SSL.”

**Software Security Pattern**

- Applying design patterns to common security approaches
- “Security Pattern are an abstraction of business problems that address a variety
  of security requirements and provide a solution to the problem.” [45]

### 6.2. Pattern-driven Security Design

#### 6.2.1. Overview

**Pattern-driven Security Design - Overview**

#### 6.2.2. Security Requirements

**Security Use Cases**
“Identify the required security features based on the functional and non-functional
requirements and organizational policies... ” [45]

#### 6.2.3. Security Architecture

**Identify Security Patterns**
“Create a conceptual security model based on architecturally significant use cases... ”
[45]

##### 71

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.1.: Pattern-driven Security Design. Based on [45]
```

**Risk Analysis**
“Perform risk analysis and mitigate risks by applying security patterns... ” [45]

**Trade-off Analysis**
“Perform trade-off analysis to justify architectural decisions... ” [45]

#### 6.2.4. Security Design

**Factor Analysis**
“Identify the factors for each component or service specific to the application of system
infrastructure... ” [45]

**Factor Analysis - Constraints**

- Identify constrains for the target application
  **-** Application-specific
  **-** Environment-specific
  **-**...
- List important security factors =⇒ Justify the use of security pattern and other
  security design decisions

**Factor Analysis - Examples**

- Target deployment platform
- Service provider infrastructure

6. Security Patterns IV Software Security for Autonomous Systems
   - Key management strategy
   - Client devices
   - Nature of business transactions
   - Service-level agreements
     -...

**Tier Analysis**
“Review the factors that impact the security of applications or Web services elements
under each logical architecture tier... ” [45]

**Factor Analysis - Details**

- Analysis security protection mechanisms and design strategies per tier
- Identify intra-tier communication requirements and dependencies

**Policy Design**
“Formulate a security policy... ” [45]

**Policy Design - Types**
“Security policies are a set of rules and practices that regulate how an application or
service provides services to protect its resources.” [45]

- Identity
- Access control
- Content-specific
- Network and Infrastructure
- Regulatory
- Advisory and Informative

**Policy Design - Policy Elements**
Additional aspects that need to be considered and that are typically application,
environment or business specific:

- User registration, revocation, and termination
- Role-based access control
- PKI management

6. Security Patterns IV Software Security for Autonomous Systems
   - Service provider trust policy
   - Data encryption and signature verification
   - Service audit and traceability
   - Password selection and maintenance
   - Information classification and labeling
   - DMZ Environment access policy
   - Application Administration
   - Remote access
   - Host and network administration
   - Application failure notice policy
   - Service continuity and recovery
     -...

**Trust Model**
“Define the object relationship for security protection and identify the associated trust
models or security policies... ” [45]

**Trust Model - What is it about?**

- Trust Model is the central aspect for a security design
- It deals with:
  **-** Where trust decision are made
  **-** What these decision include
  **-** What identity means and how it expressed
  **-** What trust relationships exist

**Threat Profiling**
“Identify any security risks or threats that are specific to the use case requirements

... ” [45]

**Threat Profiling - Example**
“Threat profiling denotes profiling architecture and application configurations for po-
tential security weaknesses.” [45]
Example: Data flow analysis

6. Security Patterns IV Software Security for Autonomous Systems

**Security Design & Security Pattern Catalog**
“Apply security patterns wherever appropriate. Sometimes re-architecting or reengi-
neering may be required... ” [45]

#### 6.2.5. Implementation

**Security Reality Check**
“Prior to implementation, use Security Reality Checks to review and assess the security
levels by logical architecture tiers... ” [45]

### 6.3. Security Pattern

**Security Pattern - Definition**
“A security pattern describes a particular recurring security problem that arises in
specific contexts, and presents a well-proven generic solution for it. The solution consists
of a set of interacting roles that can be arranged into multiple concrete design structures,
as well as a process to create one particular such structure.” [37]

- “recurring security problem”
- “well-proven generic solution”

**Security Pattern - Where to apply?**

- Software
- System
- Operational/Organizational

#### 6.3.1. Security Pattern Template

**Security Pattern Template**
Based on [45]:

- Problem
- Forces
- Solution
- Structure
- Strategies
- Consequences
- Security Factors and Risks

6. Security Patterns IV Software Security for Autonomous Systems
   - Reality Check
   - Related Patterns

**Security Pattern Template - Problem**
“Describes the security issues addressed by the pattern.” [45]

**Security Pattern Template - Forces**
“Describes the motivations and constraints that affect the security problem. Highlights
the reasons for choosing the pattern and provides justification.”[45]

**Security Pattern Template - Solution**
“Describes the approach briefly and the associated mechanisms in detail.”[45]

**Security Pattern Template - Structure**
“Describes the basic structure of the solution using UML sequence diagrams and
details the participants.”[45]

**Security Pattern Template - Strategies**
“Describes the different ways a security pattern may be implemented and deployed.”[45]

**Security Pattern Template - Consequences**
“Describes the results of using the security pattern as a safeguard and control measure.
It also describes the trade-offs.”[45]

**Security Pattern Template - Security Factors and Risks**
“Describes the factors and risks to be considered while applying the pattern.”[45]

**Security Pattern Template - Reality Check**
“Describes a set of review items to identify the feasibility and practicality of the
pattern.”[45]

**Security Pattern Template - Related Patterns**
“Lists other related patterns from the Security Patterns Catalog or from other related
sources.”[45]

#### 6.3.2. Software Security Pattern

**Software Security Pattern & Relationships**

- Software security patterns usually don’t stand alone
- They are related to and depend on other software security patterns

6. Security Patterns IV Software Security for Autonomous Systems

**Software Security Patterns [45]**
Figure 6.7 shows possible tiers in a typical enterprise application with a web fronted.

```
Figure 6.2.: Software Security Pattern Overview. Based on [45]
```

**Software Security Pattern - Web Tier**
Figure 6.22 shows relationships between software security patterns for the web tier.

```
Figure 6.3.: Sequence diagram for Web tier security patterns. Based on [45]
```

6. Security Patterns IV Software Security for Autonomous Systems

**Software Security Pattern - Business Logic Tier**

```
Figure 6.4.: Sequence diagram for Business Logic tier security patterns. Based on [45]
```

**Software Security Pattern - Web Services Tier**

```
Figure 6.5.: Sequence diagram for Web Services tier security patterns. Based on [45]
```

**Software Security Pattern - Identity Tier**

- Steps required to create single-sign-on solution
- SAML: Security Assertion Mark-up language ...

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.6.: Sequence diagram for Identity tier security patterns. Based on [45]
```

### 6.4. Introduction

**Software Security Pattern**

**General remarks on tiers and security**

**Web** • Entry point for all users

- Most frequent and initial point of attack

**Business** • Comprises components responsible for implementing the business logic

- Web and business tier maybe co-located =⇒ **_If not_** : Problem of propagating
  security context

**Services** • XML-based web services, REST APIs, Microservices,...

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.7.: Software Security Pattern Overview. Based on [45]
```

- Ideal for cross-platform solutions
- Enable dynamic combination of services

**Common Design Goals**

- Encapsulation
- Decoupling
- Re-usability
- Testability

### 6.5. Security Choke Points

**Security Choke Points - Motivation**
What are **security choke points** and why could they be a good idea?

- Centralize security controls
- Enforce security checks before business logic is executed and data accessed
- Decouple security controls from business logic
- Could become a single-point-of-failure and main target of attacks

6. Security Patterns IV Software Security for Autonomous Systems

#### 6.5.1. Secure Base Action

**Secure Base Action - Problem**
“You want to consolidate interaction of all security-related components... into a single
point of entry for enforcing security and integration of security mechanisms.” [45]
Part of the Web Tier patterns

**Secure Base Action - Forces**

- “You want to enforce security by centralizing all security-related functionality.”
  [45]
- “You want to reduce direct integration of presentation logic with security logic.”
  [45]
- “You want to encapsulate the details of the security-related components so that
  those components can be enhanced without impact to presentation logic.” [45]
- “You have several security components that you need to coordinate or orchestrate
  to ensure overall security requirements are met.” [45]

**Secure Base Action - Solution**
“Use a Secure Base Action to coordinate security components and to provide Web tier
components with a central access point for administrating security-related functionality.”
[45]

**Secure Base Action - Solution – Sequence Diagram**

```
Figure 6.8.: Sequence diagram: Secure Base Action. Based on [45]
```

6. Security Patterns IV Software Security for Autonomous Systems

**Secure Base Action - Consequences**

- Improved manageability of security requirements
- Improves reusability

#### 6.5.2. Secure Service Façade

**Secure Service Façade - Problem**
“You need a secure gateway mandating and governing security on client requests,
exposing a uniform, coarse-grained service interface over finer-grained, loosely coupled
business services that mediates client requests to the appropriate services.” [45]
Part of the Business Tier patterns

**Secure Service Façade - Forces**

- Off-load security implementations from individual service components“: You want
  to off-load security implementations from individual service components and per-
  form them in a centralized fashion so that security developers can focus on security
  implementation and business developers can focus on on business components.”[45]
- Impose security rules independent from services“: You want to impose and admin-
  ister security rules on client requests that the service implementers are unaware of
  in order to ensure that authentication, authorization, validation, and auditing are
  properly performed on all services.”[45]
- Framework for manage security context life cycle“: You want a framework to
  manage the life cycle of the security context between interactive service invocations
  by clients and to propagate the security context to appropriate services where the
  services are implemented.”[45]
- Reduce coupling between service, but maintain unified interfaces for clients“: You
  want to reduce the coupling between fine-grained services but expose a unified
  aggregation of such services to the client through a simple interface that hides the
  complexities of interaction between individual services while enforcing all of the
  overall security requirements of each service.”[45]
- Reduce message exchange between client and service“: You want to minimize the
  message exchange between the client and the services, storing the intermittent
  state and context on the server on behalf of the client instead.”[45]

**Secure Service Façade - Solution**
“Use a Secure Service Façade to mediate and centralize complex interactions between
business components under a secure session.” [45]

**Secure Service Façade - Solution - Sequence Diagram**

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.9.: Seuence diagram Secure Service Façade. Based on [45]
```

**Secure Service Façade - Consequences**

- Exposes a simplified, unified interface to a client
- Off-loads security validations from lightweight services
- Centralizes policy administration
- Centralizes transaction management and incorporates security attributes
- Facilitates dynamic, rule-based service integration and invocation
- Minimizes message exchange between client and services

#### 6.5.3. XACML Policy Enforcement

**XACML Architecture with Example**

6. Security Patterns IV Software Security for Autonomous Systems

Figure 6.10.: Source: XACML Policy Enforcement Architecture: Axiomat-
ics. https://en.wikipedia.org/wiki/XACML#/media/File:XACML*
Architecture*%26_Flow.png, CC BY 3.0 https://creativecommons.
org/licenses/by/3.0/

Abbr. Term Description
PAP Policy Administration Point Point which manages access authorization poli-
cies
PDP Policy Decision Point Point which evaluates access requests against
authorization policies before issuing access de-
cisions
PEP Policy Enforcement Point Point which intercepts user’s access request to a
resource, makes a decision request to the PDP
to obtain the access decision (i.e. access to the
resource is approved or rejected), and acts on
the received decision
PIP Policy Information Point The system entity that acts as a source of at-
tribute values (i.e. a resource, subject, environ-
ment)
PRP Policy Retrieval Point Point where the XACML access authorization
policies are stored, typically a database or the
filesystem.
Source: https://en.wikipedia.org/wiki/XACML

#### 6.5.4. Message Interceptor Gateway

**Message Interceptor Gateway - Problem**
“You want to use a single entry point and centralize security enforcement for all
incoming and outgoing XML messages.” [45]

6. Security Patterns IV Software Security for Autonomous Systems

**Message Interceptor Gateway - Forces**

- Block access to end-points: “You want to block and prevent all direct access to
  the exposed service end-points.”
- Central PEP: “You want to provide a single entry point, a centralized security
  point, and a policy enforcement point for invoking all target service endpoints.”
- “You want to centralize enforcement of identity, role and policy-based access con-
  trol for all exposed services.”
- Inspect traffic: “You want to intercept all XML traffic and inspect the complete
  XML message and attachments before processing at the service endpoint.”
- “You want to apply message inspection and filter mechanisms on the XML traffic
  based on content, payload size, and message representations.”
- Verify integrity & confidentiality: “You want to verify for message integrity and
  confidentiality during its transit, particularly for eavesdropping and tampering.”
- Enforce TLS usage: “You want to enforce transport-layer security using two-way
  SSL/TSL (mutual authentication) to achieve end-to-end data privacy and confi-
  dentiality of the communication.”
- “You want to protect the exposed WSDL descriptions from public access and
  prevent revealing operations.”
- Integrate existing identity-provider: “You want to integrate existing identity-provider
  infrastructure for authentication and authorization.”
- Detect XML replay DDos attacks: “You want to monitor and identify XML-based
  replay and DDoS attacks by tracking and verifying the IP addresses, hostnames,
  message timestamps, and other message sender-specific information.”
- Enforce interoperability: “You want to verify and validate all incoming and outgo-
  ing messages for interoperability, current standards, and regulatory compliance.”
- Centralized Logging: “You want to enforce centralized logging, monitoring, and
  management of all XML-based transports, session, transactions, and exchanged
  data.”
- Collect metrics: “You want to track usage, failures, and other service-level statis-
  tics, such as metering and billing.”
- Support XML &WS-Security standards: “You want to provide support for ver-
  ifying and validating incoming and outgoing messages based on XML standards
  such as OASIS WS-Security, XML digital signatures, XML Encryption, SAML,
  XACML, and XrML.”

6. Security Patterns IV Software Security for Autonomous Systems

**Message Interceptor Gateway - Solution**
“The Message Interceptor Gateway pattern is a proxy infrastructure providing a cen-
tralized entry point that encapsulates access to all target service endpoints of a Web
service providers.”
“ It acts as a controller that aggregates access and enforces security mechanisms on
the XML traffic by making use of identity and access management infrastructure. It
secures the incoming and outgoing XML traffic by securing the communication channels
between the service endpoints.
[45]

**Message Interceptor Gateway - Solution - Overview**

```
Figure 6.11.: Overview Message Interceptor Gateway. Based on [45]
```

6. Security Patterns IV Software Security for Autonomous Systems

**Message Interceptor Gateway - Solution - Sequence Diagram**

Figure 6.12.: Sequence diagram for Message Interceptor Gateway pattern. Based on [45]

**Message Interceptor Gateway - Consequences**

- Centralized control
- Modularity and maintainability
- Reusability
- Extensibility
- Ease of migration
- Improved testability
- Network responsiveness
- Additional expertise required

#### 6.5.5. Message Inspector

**Problem**
“You want to verify and validate the quality of message-level security mechanism
applied to XML Web services.” [45]

**Message Inspector - Forces**

- Limit messages based security token profiles and assertions: “You want to proac-
  tively identify and potentially limit messages upon receipt based on applied security
  token profiles and assertion representing the identity and policies.”[45]

6. Security Patterns IV Software Security for Autonomous Systems
   - Identify messaged replay and XML-based DoS attacks: “You want to monitor
     and identify message replay and XML-based DoS attacks by tracking and verify-
     ing encrypted communication, security tokens, XML digital signatures, message
     correlation, message expiry, or timestamps.”[45]
   - Verify & validate messages to identify parameter tampering: “You want to verify
     and validate messages at the element level to identify parameter tampering and
     message injection attacks via XPATH and XQUERY expressions.”[45]
   - Verify for interoperability and standards compliance: “You want to verify mes-
     sages for interoperability and standards compliance to guarantee that the applied
     security mechanisms of the incoming and outgoing messages work seamlessly in all
     usage scenarios.”[45]
   - Enforce centralized logging : “You want to enforce a centralized logging based on
     the security actions and decisions made on the received messages.”[45]
   - Provide uniform API for message-level security: “You want to provide a uniform
     API mechanism for managing message-level security and processing the security
     headers in accordance with various XML security standards, such as OASIS WS-
     Security, XML digital signatures, XML Encryption, SAML Token profile, and REL
     Token profile.”[45]

**Message Inspector - Solution**
“Use the Message Inspector pattern as a modular or pluggable component that can
be integrated with infrastructure service components that handle pre-processing and
post-processing of incoming and outgoing SOAP or XML messages.” [45]

6. Security Patterns IV Software Security for Autonomous Systems

**Message Inspector - Solution - Sequence Diagram Alternative I**

Figure 6.13.: Sequence diagram for Message Inspector pattern (alternative 1). Based on
[45]

**Message Inspector- Solution - Sequence Diagram Alternative II**

Figure 6.14.: Sequence diagram for Message Inspector pattern (alternative 2). Based on
[45]

**Message Inspector - Consequences**

- Modularity and maintainability
- Reusability

6. Security Patterns IV Software Security for Autonomous Systems
   - Extensibility

**Remark on Security Choke Points Patterns so far**

- Patterns presented heavily relate to web-based applications with a Java-based
  backend
- Service related patterns are very closely related to XML-based web service tech-
  nologies and OASIS standards (https://www.oasis-open.org)
- Patterns may need rework/modification for more modern approaches, like mi-
  croservices and RESTful APIs

#### 6.5.6. API Gateway

**API Gateway - Problem**
“How do the clients of a Microservices-based application access the individual ser-
vices?” https://microservices.io/patterns/apigateway.html

**API Gateway - Forces**

- “The granularity of APIs provided by microservices is often different than what a
  client needs.”
- “Different clients need different data.”
- “The number of service instances and their locations (host+port) changes dynam-
  ically”

https://microservices.io/patterns/apigateway.html

**API Gateway - Solution**

**API Gateway - Consequence**

- “Insulates the clients from how the application is partitioned into microservices”
- “Simplifies the client by moving logic for calling multiple services from the client
  to API gateway”
- “Increased complexity - the API gateway is yet another moving part that must be
  developed, deployed and managed”
- “Increased response time due to the additional network hop through the API gate-
  way”

https://microservices.io/patterns/apigateway.html

6. Security Patterns IV Software Security for Autonomous Systems

Figure 6.15.: API Gateway pattern. Source: https://microservices.io/patterns/
apigateway.html

### 6.6. Authentication & Authorization

**Authentication & Authorization - Motivation**

- Identifying users, software components or devices is a common security task
- Limiting access to resources, services, etc. is also a common security goal

#### 6.6.1. Access Token

**Access Token - Problem**
“How to communicate the identity of the requestor to the services that handle the
request?” https://microservices.io/patterns/apigateway.html

**Access Token - Forces**

- “Services often need to verify that a user is authorized to perform an operation”

https://microservices.io/patterns/apigateway.html

**Access Token - Solution**
“The API Gateway authenticates the request and passes an access token (e.g. JSON
Web Token) that securely identifies the requestor in each request to the services. A
service can include the access token in requests it makes to other services.” https:
//microservices.io/patterns/apigateway.html

6. Security Patterns IV Software Security for Autonomous Systems

**Access Token - Consequences**

- “The identity of the requestor is securely passed around the system”
- “Services can verify that the requestor is authorized to perform an operation”

https://microservices.io/patterns/apigateway.html

#### 6.6.2. Authentication Enforcer

**Authentication Enforcer - Problem**

- “You need to verify that each request is from an authenticated entity, and since
  different classes handle different requests, authentication code is replicated in many
  places and the authentication mechanism can’t easily be changed.” [45]
- “HTTP basic authentication, form-based authentication, client certificate-based
  authentication, Single sign-on,... : May be possible/required for an application”
  [45]

**Authentication Enforcer - Forces**

- “Access to application is restricted to valid users, and those users must be properly
  authenticated.”[45]
- “There may be multiple entry points into an application, each requiring user
  authentication.”[45]
- “It is desirable to centralize authentication code and keep it isolated from the
  presentation and business logic.”[45]

**Authentication Enforcer - Solution**
“Create a centralized authentication enforcement that performs authentication of users
and encapsulates the details of the authentication mechanism.” [45]

6. Security Patterns IV Software Security for Autonomous Systems

**Authentication Enforcer - Solution - Sequence Diagram I**

```
Figure 6.16.: Sequence diagram for Authentication Enforcer pattern. Based on [45]
```

**Identity** “Identity is simply a computer‘s representation of an entity.” [5]

**Principal** “A principal is a unique entity. An identity specifies a principal.” [5]

**Subject** Represents an entity. May contain multiple authenticated principals and cre-
dentials.

6. Security Patterns IV Software Security for Autonomous Systems

**Authentication Enforcer - Solution - Sequence Diagram II**

Figure 6.17.: Sequence diagram for the Authentication Enforcer pattern with JAAS.
Based on [45]

**Authentication Enforcer - Consequences**

- Reduced Code
- Consolidation of authentication and verification to one class

#### 6.6.3. Authorization Enforcer

**Authorization Enforcer - Problem**
“Many components need to verify that each request is properly authorized at the
method and link level. For applications that cannot take advantage of container-managed
security; this custom code has the potential to be replicated.” [45]

**Authorization Enforcer - Forces**

- “You want to minimize the coupling between the view presentation and the security
  controller.”[45]
- “Authorization logic required to be centralized and should not spread all over the
  code base in order to reduce risk of misuse or security holes.”[45]
- “Authorization should be segregated from authentication logic to allow for evolu-
  tion of each without impacting the other.”[45]

6. Security Patterns IV Software Security for Autonomous Systems

**Authorization Enforcer - Solution**
“Create an Access Controller that will perform authorization checks using standard
Java security API classes.”[45]

**Authorization Enforcer - Solution - Sequence Diagram**

```
Figure 6.18.: Sequence diagram for the Authorization Enforcer pattern. Based on [45]
```

**Authorization Enforcer - Consequences**

- Centralized control
- Improves reusability
- Promotes separation of responsibility

### 6.7. Logging and Auditing

**Logging & Auditing - What is the difference?**

**Logging** Aims at keeping track of activities within an application, can be used for in-
trusion detection or debugging

**Auditing** Keeping a (secure) record of business related transaction, may be required by
regulatory rules

Software security patterns address this for cases were access control and tamper-
proofed logging and auditing is required

6. Security Patterns IV Software Security for Autonomous Systems

#### 6.7.1. Secure Logger

**Secure Logger - Problem**
“All application events and related data must be securely logged for debugging and
forensic purposes. This can lead to redundant and complex logic.” [45]
Part of the Web Tier patterns

**Secure Logger - Forces**

- “You need to log sensitive information that should no be accessible to unauthorized
  users.” [45]
- “You need to ensure the integrity of the data logged to determine if it was tampered
  with by an intruder.” [45]
- “You want to capture output at one level for normal operations and at other levels
  for greater debugging in the event of a failure or an attack.” [45]
- “You want to centralize control of logging in the system for management purposes.”
  [45]
- “You want to apply cryptographic mechanisms for ensuring confidentiality and
  integrity of the logged data.” [45]

**Secure Logger - Solution**
“Use a Secure Logger to log messages in a secure manner so that they cannot be easily
altered or deleted and so that events cannot be lost.” [45]

**Secure Logger - Solution Variant - Local Logging**

```
Figure 6.19.: Sequence diagram for the Secure Logger pattern. Based on [45]
```

6. Security Patterns IV Software Security for Autonomous Systems

**Secure Logger - Solution Variant - Remote Logging**

Figure 6.20.: Sequence diagram for the Secure Logger pattern with remote logging.
Based on [45]

**Secure Logger - Consequences**

- Centralized logging control
- Prevents undetected log alteration
- Reduces performance
- Promotes extensibility
- Improves manageability

#### 6.7.2. Audit Interceptor

**Audit Interceptor - Problem**
“You want to intercept and audit requests and responses to and from the Business
tier.” [45]
Part of the Business Tier patterns

**Audit Interceptor - Forces**

- “You want centralized and declarative auditing of service requests and responses.”
  [45]
- “You want auditing of services decoupled from applications themselves.” [45]
- “You want pre- and post-process audit handling of service requests, response errors,
  and exceptions.” [45]

6. Security Patterns IV Software Security for Autonomous Systems

**Audit Interceptor - Solution**
“Use an Audit Interceptor to centralize auditing functionality and define audit events
declaratively, independent of the Business tier service.” [45]

**Audit Interceptor - Solution - Sequence Diagram**

```
Figure 6.21.: Sequence diagram for the Audit Interceptor pattern. Based on [45]
```

**Audit Interceptor - Consequences**

- Centralized, declarative auditing of service requests
- Pre- and post-process audit handling of service requests
- Auditing of services decoupled from the services themselves
- Supports evolving requirements and increases maintainability
- Reduces performance

### 6.8. Summary

#### 6.8.1. Pattern Overview

**Summary - Software Security Pattern - Web Tier**
Figure 6.22 shows relationships between software security patterns for the web tier.

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.22.: Software Security Pattern - Web Tier. Based on [45]
```

**Software Security Pattern - Business Logic Tier**

```
Figure 6.23.: Software Security Pattern - Business Logic Tier. Based on [45]
```

**Software Security Pattern - Web Services Tier**

#### 6.8.2. Using Software Security Patterns

**Summary - Using Patterns**

- Take security into account during the design phase and decide between

6. Security Patterns IV Software Security for Autonomous Systems

```
Figure 6.24.: Software Security Pattern - Web Services Tier. Based on [45]
```

**-** Tightly-coupled security mechanisms =⇒ integrated into the application logic
**-** Loosely-coupled security mechanisms =⇒ separated from application logic

- Design cannot be completely abstracted from technology choices
- Search for security and other patterns
  **-** Security goals
  **-** Selected technology
  **-** Application domain

**Beware of changing technology landscape**

- Security Pattern from [45] collection is from 2005, it does not consider new tech-
  nology trends like:
  **-** OAuth
  **-** REST
  **-** Microservices
- Patterns may already be provided by APIs/frameworks to realize these newer
  approaches
- The idea of presented patterns remains valid, but requires a very different way to
  design and implement them

6. Security Patterns IV Software Security for Autonomous Systems

**Security Pattern Resources**

- Summary of the presented patterns: [http://coresecuritypatterns.com/patterns.](http://coresecuritypatterns.com/patterns.)
  htm
- The Open Group: https://publications.opengroup.org/g031
- Carnegie Mellon University: https://resources.sei.cmu.edu/library/asset-view.
  cfm?assetid=9115
- Microservice patterns: [http://microservices.io/patterns/index.html](http://microservices.io/patterns/index.html)
- Many books on design patterns for different kind of problems, e.g. services, parallel
  computing,...

## 7. System Security Patterns

### 7.1. Introduction

**Software vs System Security Patterns**

- Software Security Pattern focus on an application and its implementation
- System Security Pattern also consider the surrounding IT environment and pro-
  cesses

**Why are System Security Patterns important?**

- System Security Pattern extend/augment Software Security Pattern
- Prevent you from building a steel door into a tent

Figure 7.1.: Security measures without considering the environment are problematic. Be-
cause the environment may allow an easy bypass. Source unknown, appears
on many web pages without source attribution.

##### 102

7. System Security Patterns IV Software Security for Autonomous Systems

### 7.2. Access Control Model Patterns

“High-level models represent the security policies of the enterprise. These models define
security constraints at an architectural level, the application level, and are enforced by
the lower levels.” [37]

**Authorization:** “...who is authorized to access specific resources in a system, ...” [37]

**Role-Based Access Control:** “... how to assign rights based in the functions or tasks
of people in an environment... ” [37]

**Multilevel Security:** “... how to categorize sensitive information and prevent its disclo-
sure.” [37]

**Reference Monitor:** “... enforces declared access restrictions when an active entity
requests resources.” [37]

**Role Rights Definition:** “... provides a way,... , of assigning rights to roles to imple-
ment a least-privilege policy.” [37]

```
Figure 7.2.: Overview of access control patterns. Based on [37]
```

#### 7.2.1. Role-based Access Control

**Role-based Access Control - Problem**
“For convenient administration of authorization rights we need to have ways to factor
out rights. Otherwise, the number of individual rights is just too large,... ” [37]

7. System Security Patterns IV Software Security for Autonomous Systems

**Role-based Access Control - Forces**

- Classification of people by functions “In most organizations people can be classified
  according to their functions or tasks” [37]
- Performing tasks requires sets of rights “Common tasks require similar sets of
  rights”[37]
- Need-to-know Policy “We want to help the organization to define precise access
  rights for its members according to a need-to-know policy.”[37]

**Role-based Access Control - Solution**
“Most organizations have a variety of job functions that require different skills and
responsibilities. For security reasons, users should get rights based on their job functions
or their assigned tasks.” [37]

**Role-based Access Control - Overview**

```
Figure 7.3.: Main components for RBAC. Based on [37]
```

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Core RBAC**

```
Figure 7.4.: Core RBAC. Based on [11]
```

“Core RBAC model element sets and relations are defined in Figure 1. Core RBAC
includes sets of five basic data elements called users (USERS), roles (ROLES), objects
(OBS), operations (OPS), and permissions (PRMS). The RBAC model as a whole is
fundamentally defined in terms of individual users being assigned to roles and permis-
sions being assigned to roles. As such, a role is a means for naming many-to-many
relationships among individual users and permissions. In addition, the Core RBAC
model includes a set of sessions (SESSIONS) where each session is a mapping between
a user and an activated subset of roles that are assigned to the user.”[11]

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Hierarchical RBAC**

```
Figure 7.5.: Hierarchical RBAC. Based on [11]
```

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Hierarchical RBAC with Static Separation of Duty**

**Static Separation of Duty:** “SSD relations place constraints on the assignments of users
to roles. Membership in one role may prevent the user from being a member of
one or more other roles, depending on the SSD rules enforced.”[11]

**Static Separation of Duty in the Presence of a Hierarchy:** “This type of SSD relation
works in the same way as basic SSD except that both inherited roles as well as
directly assigned roles are considered when enforcing the constraints.”[11]

```
Figure 7.6.: Hierarchical RBAC with static separation of duty. Based on [11]
```

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Dynamic Separation of Duty and RBAC**
“Dynamic separation of duty (DSD) relations, like SSD relations, limit the permissions
that are available to a user. However DSD relations differ from SSD relations by the
context in which these limitations are imposed. DSD requirements limit the availability
of the permissions by placing constraints on the roles that can be activated within or
across a user’s sessions.”[11]

```
Figure 7.7.: Dynamic separation of duty and RBAC. Based on [11]
```

**Role-based Access Control - Consequences**

- Reduced complexity “It allows administrators to reduce the complexity of security,
  because there are much more users than roles.”[37]
- Reflection of job functions “Organization policies about job functions can be re-
  flected directly in the definition of roles and the assignment of users to roles.”[37]
- Easy adaption to user status changes “It is very simple to accommodate users
  arriving, leaving, or being reassigned. All these actions requires only manipulation
  of the associations between users and roles.”[37]

```
Additional consequences:
```

- “Roles can be structured for further flexibility and reduction of rules.”[37]
  “Users can activate more than one session at a time for functional flexibility – some
  tasks may require multiple views or different types of actions.”[37]
  “We can add UML constraints to indicate that some roles cannot be used in the
  same session or given to the same user (separation of duties).”[37]

7. System Security Patterns IV Software Security for Autonomous Systems

```
“Groups of users can be used as role members, further reducing the number of
authorization rules and the number of role assignments.”[37]
```

### 7.3. Firewalls

**Firewalls - Overview**

```
Figure 7.8.: Firewalls. Based on [37]
```

#### 7.3.1. Packet Filter Firewall

**Packet Filter Firewall - Problem**
“Some hosts on other networks may try to attack the local network through their
IP-level payloads.... How can we identify and block those hosts?” [37]

**Packet Filter Firewall - Forces**

- Network connection required “We need to communicate with other networks, so
  isolating our is not an option. However, we do not want to take a high risk for
  doing so.” [37]
- Protection mechanisms, should reflect organizational security policy “The pro-
  tection mechanism should be able to reflect precisely the security policy of the
  organization. A too coarse defence may not be useful.” [37]
- Protection should be transparent to users “Any protection mechanism should be
  transparent to the users. Users should not need to perform special actions to be
  secure.” [37]
- Overhead cost should be low “The cost and overhead of the protection mechanism
  should be relatively low or the system may become to expensive to run.” [37]

7. System Security Patterns IV Software Security for Autonomous Systems
   - Clear protection model needed “Network administrators deploy and configure a
     variety of protection mechanisms; hence it s important to have a clear model of
     what is being protected.” [37]
   - Attacks change, defences must be easy to change “The attacks are constantly
     changing; hence it should be easy to make changes to the configuration of the
     protection mechanism.” [37]
   - Audit connections “It may be necessary to log input and/or output for auditing
     and defence purposes.” [37]

**Packet Filter Firewall - Solution**
“A Packet Filter Firewall... intercepts all traffic coming and going from a port P
and inspects is packets.... Those coming from or going to mistrusted addresses are
rejected.” [37]

**Solution - Overview**

```
External host Internet Packet filter firewall Local host
```

```
Port
```

```
request request
```

```
Internal network
```

```
Figure 7.9.: Packet Filter Firewall. Loosely based on [37]
```

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Sequence Diagram**

```
Figure 7.10.: Sequence diagram for a packet filter firewall. Based on [37]
```

**Packet Filter Firewall - Consequences**

- Filters all traffic “A firewall transparently filters all the traffic that passes through
  it, thus lowering the risk of communication with potential hostile networks.” [37]
- Allows expressing company security policies“It is possible to express the organiza-
  tion’s filtering policies through its filtering rules, with different levels of protection
  for different parts of the network.” [37]
- Easy rule change “It is easy to update the rule set to counter new threats.” [37]
- Allows recoding of all messages “Because it intercepts all requests, a firewall allows
  systematic logging of incoming and outgoing messages. Because of this, a firewall
  facilitates the detection of possible attacks and helps to hold local users responsible
  for their actions when interacting with external networks.” [37]
- Low cost and included in many routers/OS “Its low cost enabled it to be included
  as part of many operating systems and simple network devices such as routers.”
  [37]
- Good performance “It offers good performance, only needing to look at the headers
  of IP packets rather than the complete packet.” [37]
- Can be combined with IDS “It can be combined with intrusion detection systems
  (IDS) for greater effectiveness. In this case, the IDS can tell the firewall to block
  suspicious traffic.”[37]

7. System Security Patterns IV Software Security for Autonomous Systems

#### 7.3.2. Stateful Firewall

**Stateful Firewall - Problem**
“How can we correlate incoming packets?”[37]

**Stateful Firewall - Problem illustration**

```
External host Packet filtering firewall Local host
```

```
Establish connection to port 80 Check rules and allow access
```

```
Establish connection to port 80
Use next free port for receiving future packets in session
```

```
Next packet in session
Block packet: Session port not in allowed list
```

Figure 7.11.: For TCP connections a server typically opens a new server socket and uses
any free port for this. If the firewall does not track this, any future packets
to the server in the same connection/session will be blocked. The chosen
free port will not be in the predefined list of allowed ports.

**Stateful Firewall - Forces**

- Similar to packet filtering firewalls
- Packets need to be correlated to service connections→ rules cannot be formulated
  for packets alone

**Stateful Firewall - Solution**
“Keep a list or table (a dynamic rule set) with the connections that have been opened,
and correlate the type of messages received or sent.” [37]

7. System Security Patterns IV Software Security for Autonomous Systems

**Solution - Overview**

```
External host Stateful firewall Local host
```

```
Establish connection to port 80 Check rules and allow access
```

```
Establish connection to port 80
Use next free port for receiving future packets in session
```

```
Next packet in session Allow: Next packet in session
```

Figure 7.12.: Stateful firewall allows packets to pass through without the need to explic-
itly allowing a port.

**Solution - Sequence Diagram**

```
Figure 7.13.: Sequence diagram for a stateful firewall. Based on [37]
```

**Stateful Firewall - Consequences**

- Easy to set up state table “It is relatively easy to set up the state table once we
  know what attacks we are expecting.” [37]
- Low cost “It has a low implementation cost, as it requires only a state table.” [37]

7. System Security Patterns IV Software Security for Autonomous Systems
   - Good performance“It offers good performance. It only needs to look at packet
     headers for new connections. For existing connections it looks only at the state
     table.” [37]
   - Enhances packet filtering firewall “It can enhance the security of the other types of
     firewalls by adding information from different levels bout correlated packets.” [37]

### 7.4. Secure Internet Applications

**Secure Internet Applications - Overview**

```
Figure 7.14.: Patterns for securing Internet applications. Based on [37]
```

#### 7.4.1. Demilitarized Zone

**Demilitarized Zone - Problem**
“Internet technology systems, particularly those facing the public Internet, are regu-
larly subject to attacks against their functionality, resources and information. How do
we protect our systems from direct attacks?”[37]

**Forces**

- “The cost of an extensive security solution will be high, but the cost of an intrusion
  may also be high in terms of system damage, theft and loss of customer confidence.”
  [37]
- “To prevent attack, we must make intrusions into any part of the system as difficult
  as possible, especially an organizations internal business systems.”[37]

7. System Security Patterns IV Software Security for Autonomous Systems

**Demilitarized Zone - Solution**

- “Provide a region of the system that is separated from both the external users and
  the internal data and functionality... ”[37]
- Locate servers in DMZ that need to be accessed from the outside
- Restrict access to DMZ as much as possible, from out- and inside

**Demilitarized Zone - Example**

```
Figure 7.15.: Example network with a DMZ
```

**Demilitarized Zone - Consequences**

- Improved security “ Security is improved, because fewer systems are exposed to
  attack and multiple firewall artifacts must be breached to compromise security.”
  [37]
- Variable levels of depth for protection “The level and depth of protection can be
  varied to match the anticipated risk and the cost limitations.” [37]
- Reduced effort for hardening hosts “Fewer hosts most be hardened to withstand
  attack than if they were all exposed to the outside world.” [37]
- “The additional security is transparent to the users of the system functionality and
  to the developers of such functionality.” [37]

7. System Security Patterns IV Software Security for Autonomous Systems

#### 7.4.2. Protection Reverse Proxy

**Protection Reverse Proxy - Problem**
“Even if you install a simple Packet Filter Firewall... your Web server can remain
vulnerable.... How can you protect your Web server infrastructure... ?” [37]

**Protection Reverse Proxy - Forces**

- Firewalls only filter unwanted protocols, Web server remains vulnerable to Web
  protocol based attacks.
- Firewalls work on the packet level and not the application level.
- Hardening the Web server itself may not be possible or to complex.

```
Adapted from [37].
```

**Protection Reverse Proxy - Solution**
“Change your network topology to use a Protection Reverse Proxy... that shields
your Web servers” [37] Shielding is achieved by filtering Web traffic and no real network
traffic reaches the server.

**Solution - Overview**

Figure 7.16.: Protection Reverse Proxy is placed in DMZ and brokers access to the actual
web server. It checks and filters requests to the web server.

7. System Security Patterns IV Software Security for Autonomous Systems

**Protection Reverse Proxy - Consequences**

- Attackers can no longer exploit vulnerabilities directly.
- Provides an additional defence-in-depth layer.
- Introduces a new potential (single) point of failure.
- Additional traffic, filtering and validation add latency to the communication.

Adapted from [37].

#### 7.4.3. Integration Reverse Proxy

**Integration Reverse Proxy - Overview**

Figure 7.17.: An Integration Reverse Proxy provides a single external interface to mul-
tiple Web servers, that should be accessible to the outside. Also provides
the option to enforce common logging and security rules.

#### 7.4.4. Summary

**Summary Security Patterns**

- Patterns from today and last week are partially very similar and address the com-
  parable problems
  **-** API Gateway (Microservices), Message Interceptor Gateway (Web Service)
  and Integration/Protection Reverse Proxy(Web Server)
- Actual implementation/usage typically involves combining patterns
- Not every pattern makes always sense to use, there are also drawbacks involved

## 8. Technology Selection

### 8.1. Introduction

**Choosing Technologies**

- Requirements gathering and design are in large parts independent from concrete
  technology choices
- Choices haven an impact on security
- May lead to additional requirements and design changes

### 8.2. Fundamental Decisions

**Main Choices**

1. Programming language(s)
2. Service/communication technologies
3. Supported Operating Systems

**Programming language(s)**

- Typical errors and weaknesses
- Community support
- Availability of tools and libraries

**Service/communication technologies**

- Programming language support
- Community support
- Availability of tools and libraries
- Standards that need to be considered

##### 118

8. Technology Selection IV Software Security for Autonomous Systems

**Supported Operating Systems**

- Availability of programming languages
- Availability of service/communication technologies
- Community support
- Availability of tools and libraries
- Guidelines that need to be considered

### 8.3. Making Choices

#### 8.3.1. Programming Languages

**Bad reason - Choose-C-for-efficiency**

- Efficiency requirements outweigh other requirements
- C is chosen without considering if efficiency requirements could be met by other
  languages as well
- Software risk management process may be executed improperly

```
Adapted from [51]
```

**Bad reason - Familiarity/Comfort with a language**

- May indicate an immature software risk management process

```
Adapted from [51]
```

**Bad reason - Ignoring impact on software security**

- Not all languages are equally secure

```
Adapted from [51]
```

**Things you should consider**

- Reliability
  **-** Error handling
  **-** Input validation
- Vulnerability to buffer overflows
- Security features a language provides

```
Adapted from [51]
```

8. Technology Selection IV Software Security for Autonomous Systems

#### 8.3.2. Service/communication technologies

**Things you should consider**

- Security features
- Interoperability
- Possible choices
  **-** CORBA
  **-** DCOM
  **-** EJB and RMI
  **-** Web services
  **-** REST

**Related to programming language choice**
For example Java and Spring framework:

- Remote Method Invocation (RMI)
- Spring’s HTTP invoker
- Hessian
- Burlap
- JAX-RPC
- JAX-WS
- JMS
- JAX-RS
- REST
  -...
  “Spring’s HTTP invoker. Spring provides a special remoting strategy which allows for
  Java serialization via HTTP” [http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/](http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/)
  html/remoting.html
  “Hessian is a binary web service protocol that makes web services usable without
  requiring a large framework, and without learning a new set of protocols. Because it
  is a binary protocol, it is well-suited to sending binary data without any need to ex-
  tend the protocol with attachments” [http://en.wikipedia.org/wiki/Hessian*(web*](http://en.wikipedia.org/wiki/Hessian*(web*)
  service_protocol)
  “Burlap. Burlap is Caucho’s XML-based alternative to Hessian.” [http://docs.](http://docs.)
  spring.io/spring/docs/3.0.x/spring-framework-reference/html/remoting.html

8. Technology Selection IV Software Security for Autonomous Systems

#### 8.3.3. Other

**Choosing an Operating Systems**

- Mostly depend on application/service requirements
- Security requirements may lead to choose a OS with higher standards of security
  directly supported by OS

**Authentication Technology**
“... choosing a reasonable authentication technology is important.” [51]

- Password-based
  **-** difficult to get right, since users often pick bad passwords
- Host-based
  **-** Uses IP or MAC addresses
- Physical Tokens
- Biometric Authentication
- Cryptographic Authentication
- Defense in Depth and Authentication

### 8.4. Additional Choices

**Initial choices done. What could be next?**

- Assume we choose:
  **-** Java
  **-** Web Services
  **-** Mac OS X, Linux and Windows
  **-** Password-based and cryptographic authentication

```
Are these all our choices or what would be next?
Answer : No, we have to choose tools and libraries
```

8. Technology Selection IV Software Security for Autonomous Systems

#### 8.4.1. Java

**Which JVM/JDK**

- Different virtual machine implementations from different vendors
  **-** May impact performance
  **-** License issues to consider
  **-** Availability of long term support
- Operating systems
  **-** May cause platform dependent failures
- Version
  **-** Newest version installed on target computers? Update maybe not be possible.

**Java Security Tools**

- policytool – Manages Java security policies
- keytool – Manages X.509 certificates and private keys
- jarsigner – digital signs JAR files
- Tools for Kerberos

**Java Security and Cryptography APIs**

- SecurityManager and security policies
- Signed and guarded objects
- Hash algorithms
- Symmetric key algorithms
- Asymmetric key algorithms
- Digital signature algorithms
- Secure random number generation
- PKI (X.509)
- PKCS#11 (Cryptographic Token Interface Standard)
- Java Secure Socket Extension (JSSE)
- XML digital signatures
- JAAS

8. Technology Selection IV Software Security for Autonomous Systems

**Java Authentication and Authorization Service (JAAS)**
“JAAS can be used for two purposes:

- for authentication of users, to reliably and securely determine who is currently
  executing Java code, regardless of whether the code is running as an application,
  an applet, a bean, or a servlet; and
- for authorization of users to ensure they have the access control rights (permissions)
  required to do the actions performed.
  JAAS implements a Java version of the standard Pluggable Authentication Module
  (PAM) framework.”
  [http://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASRefGuide.](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASRefGuide.)
  html

**What if I need more?**
Cryptography providers for additional algorithms or features, for example:

- IAIK JCE: [http://jce.iaik.tugraz.at](http://jce.iaik.tugraz.at)
- The Legion of the Bouncy Castle: [http://www.bouncycastle.org](http://www.bouncycastle.org)
  Look at alternatives, for example
- Jasypt (Java Simplified Encryption): [http://www.jasypt.org](http://www.jasypt.org) _Note:_ Not updated
  since 2014

**Spring Security**
“Spring Security is a powerful and highly customizable authentication and access-
control framework. It is the de-facto standard for securing Spring-based applications”
[http://docs.spring.io/spring-security/site/](http://docs.spring.io/spring-security/site/) Supports also

- SAML
- OAuth
- Kerberos

**XACML**
Balana

- Open source XACML implementation based on sun-xacml
- https://github.com/wso2/balana
  Sun XACML
- [http://sunxacml.sourceforge.net](http://sunxacml.sourceforge.net)
- No updates since 2006
- Partial implmentation of XACML 2.0

8. Technology Selection IV Software Security for Autonomous Systems

**Web Services - Axis2**

- [http://axis.apache.org](http://axis.apache.org)
- Apache Rampart
  **-** WS-Security
  **-** WS-Trust
- Apache Sandesha
  **-** WS-ReliableMessaging

**Spring Web Services**

- https://spring.io/projects/spring-ws
- Supports WS-Security
- Uses Spring Security

**A few more...**

- Apache Shiro
  **-** https://shiro.apache.org
  **-** “... is a powerful and easy-to-use Java security framework that performs au-
  thentication, authorization, cryptography, and session management.”
- Apache Sentry
  **-** https://sentry.apache.org
  **-** “Apache Sentry is a system for enforcing fine grained role based authorization
  to data and metadata stored on a Hadoop cluster.”
- Apache Syncope
  **-** https://syncope.apache.org
  **-** “Apache Syncope is an Open Source system for managing digital identities in
  enterprise environments, implemented in Java EE technology... ”

**Dependencies between choices**

- Different choices cannot always be combined very well
- Side effects need to be considered
  **-** Logging APIs used by libraries
  **-** Conflicting dependencies

8. Technology Selection IV Software Security for Autonomous Systems

### 8.5. Summary

**Hints for making a choice**

- Consider the existing environment
- Last update of library, web page,...
- Recent activities
- User (developer) community
- Well-known source
- Trial implementations

**Additional hints**

- Stay current
  **-** Know different technologies
  ∗ Libraries
  ∗ Tools
  ∗ Programming Languages
- Be aware of strengths and weaknesses

**Stay current: Example Microservices**

- Microservices are a new way of looking at distributed systems/applications and
  service-oriented solution: prominent example Netflix
- But this new concepts also results in various new choices
  **-** Microprofile (http://microprofile.io)
  **-** Vert.x (http://vertx.io)
  **-** Sprint Boot (https://spring.io/projects/spring-boot)
  **-**...

## 9. Development

### 9.1. Introduction

**SDLC - Development**

- Development is just one phase in the SDLC and you should not rush to it
- It is more than to successfully compile and run a program
- Focus for the lecture will be on good general practises for development and not
  language specific topics
- Lecture splits in _development_ and _testing_ , but this should be done in parallel,
  maybe even with a test-driven approach
- Some of the following points can also be applied to earlier and later phases

### 9.2. Guiding principles & deadly sins

**Secure the weakest link**

- Cryptography can provide integrity and confidentiality
- Even weak cryptography requires high effort/resources to break
- **Weak spot:** Attackers may target data before or after en-/decryption
- **Weak spot:** Especially, a problem if you don’t have end-to-end security −→
  design patterns that intercept and analyse requests before service provider handle
  them

**Practise defence in depth**

- Choose design pattern that introduce security layers
- Careful consider performance and security requirements
- **Example** Apply SSL/TLS on the transport layer and encryption on the applica-
  tion layer −→ better security, but slower communication

##### 126

9. Development IV Software Security for Autonomous Systems

**Fail securely and handle errors**

- Very important aspect during the development
- Carefully think about possible failures and how to handle them, you MUST have
  error handling
- Pay attention to not disclosing to much information in error messages
- **Example:** Handling of invalid inputs, e.g. failing to consider erroneous input (==
  garbage)
- _How?_ Refactor code for better testability

**Least Privilege**

- Original idea around since 1975, see [33]
- but still not common practice
- Does not apply only to access control permissions
  **-** But also to API design
  ∗ **Example:** Java, use of public, private or final keywords, and
  ∗ supporting security managers and permissions in Java

**Compartmentalize & Encapsulation**

- Easier to set-up privileges and follow the _Least Privilege Principle_
- Danger to violate the _Keep it Simple Principle_
- Defining boundaries between components
- Defining trust relationships between components
- API visibility

**Keep It Simple, Stupid! (KISS)**

- Problems with **complexity** :
  **-** Increases likelihood of bugs or other issues
  **-** More difficult to understand
  **-** More difficult to maintain
- **Solution:** Reuse existing components→ don’t reinvent the wheel, e.g. re-implement
  AES

9. Development IV Software Security for Autonomous Systems
   - **Attention:** Balance principles, KISS and defence in depth, with the needs for
     your project
   - **Example:** Introduce “choke points” for security functions

**Promote Privacy**

- Privacy is one central security aspects
- Privacy and usability are competing requirements
  **- Example** : Forget or store user credit card numbers
- Also ensure not to provide unnecessary information about your IT environment
- Promote privacy for users, system and code

**Hiding Secrets is Hard**

- **Example:** Copy protection on DVDs
- Passwords and cryptographic keys are needed by an application, how to hide those
  secrets?
- Security products mostly protect the perimeter, insiders are a common threat
- In general putting together security features is hard to get right

**Be Reluctant to Trust**

- Don’t put secrets in the code, e.g. passwords...
- Be sceptical
  **-** Don’t trust security product vendors

**Use Community Resources**

- Cryptographic algorithms should be public
- Don’t invent your own cryptography
- Use widely available and used security libraries

9. Development IV Software Security for Autonomous Systems

**Input validation and representation**

- You always validate input:
  **-** Metacharacters
  **-** alternate encodings
  **-** numeric representations
- **Deadly sin:** Not checking input at all, could lead to
  **-** buffer overflows
  **-** cross-site scripting attacks
  **-** SQL injection

**Honour API contracts**

- API: contract between caller and callee
- Two cases
  1. Caller does not honour the contract **Example:** Does not check for null, if
     that is a possible return value
  2. Callee does not honour the contract **Example:** Subclassing SecureRandom
     and not returning secure random values

**Always consider time and state**

- Usually there is more than one thread of control
- Things may happen in parallel
  **-** Events may occur at the same time in different threads on different CPU cores
  **-** Clock synchronization in distributed environments
  **-** Race conditions: Read and write to permissions

**You should pay attention to code quality**

- Bad code quality leads to:
  **-** Bad usability
  **-** Unpredictable behaviour
  **-** Bad maintainability
  **-** Opportunities for attackers

9. Development IV Software Security for Autonomous Systems

**Consider the environment**

- Software exist in an environment: Computer, network,...
- Environment needs to be taken into account

**Summary**
10 Guiding Principles for Software Security [51] and seven (+1) deadly sins [48]

1. Secure the weakest link
2. Practice defense in depth
3. Fail securely
4. Follow the principle of least privilege
5. Compartmentalize
6. Keep it simple
7. Promote privacy
8. Remember that hiding secrets is hard
9. Be reluctant to trust
10. Use your community resources
11. input validation and representation
12. API abuse
13. security features
14. time and state
15. errors
16. code quality
17. encapsulation
18. environment

### 9.3. Coding and security

**Introduction**

- Follow best-practice for your chosen languages and technologies
- Audit your software
- Test your software

9. Development IV Software Security for Autonomous Systems

**Software Audits**
“Without question, the top two best practices are code review (with a static analysis
tool) and architectural risk analysis.” [27]

- Architectural risk analysis part of the design step
- Code review part of development

**Code Audit vs. Architecture Audit**

- Only looking for security problems after the implementation is too late
- Code audits can find flaws, but may overlook major architecture flaws
- A good design does not prevent implementation flaws
- Architecture level → make sure the architecture is sound before implementation
- Software level → make sure the implementation is sound

#### 9.3.1. Code review

**Why use code review tools?**
“Many security problems are caused by simple bugs that can be spotted in code.”[27]
“Using a tool makes sense because code review is boring, difficult, and tedious.”[27]
**Example:** Buffer overflows

- Often caused by string operations in C code, e.g. strcpy()

**Code review tools**
Tools for Java:

- Checkstyle: [http://checkstyle.sourceforge.net](http://checkstyle.sourceforge.net)
- PMD [http://pmd.sourceforge.net](http://pmd.sourceforge.net)
- SpotBugs (formerly FindBugs) https://spotbugs.github.io
- SonarQube [http://www.sonarqube.org](http://www.sonarqube.org)

More: https://dwheeler.com/essays/static-analysis-tools.html (list was last
updated in 2015)

9. Development IV Software Security for Autonomous Systems

**SpotBugs and Eclipse**

Figure 9.1.: SpotBugs results for DAI Labs network monitoring and vulnerability appli-
cation. Analysed code had around 185 classes and 15.000 lines of code.

**SonarQube**

Figure 9.2.: Analysis results overview in SonarQube for the code that was also analysed
with SpotBugs.

**“A Fool with a Tool is still a Fool”**
[http://www.dwheeler.com/flawfinder](http://www.dwheeler.com/flawfinder)
iDEFENSE Security Advisory 03.01.05 on RealNetworks RealPlayer (CVE-2005-0455),
a security vulnerability was in this pair of lines:

9. Development IV Software Security for Autonomous Systems

```
cha r tmp [ 2 5 6 ] ;
s t r cpy( tmp , pSc r e enS i z e) ;
“Fix” for the problem was:
cha r tmp [ 2 5 6 ] ; /∗ Fl awf i nde r : i gnor e∗/
s t r cpy( tmp , pSc r e enS i z e) ; /∗ Fl awf i nde r : i gnor e∗/
```

**“Never fix a bug you don’t understand”**
“Two years ago, they ‘fixed’ a ‘problem’ in OpenSSL reported by valgrind... by re-
moving any possibility of adding any entropy to OpenSSL’s pool of randomness... ”
“The result of this is that for the last two years (from Debian’s Etch release until
now), anyone doing pretty much any crypto on Debian (and hence Ubuntu) has been
using easily guessable keys. This includes SSH keys, SSL keys and OpenVPN keys.”
[http://www.links.org/?p=327](http://www.links.org/?p=327)
Developer discussion: [http://bugs.debian.org/cgi-bin/bugreport.cgi?bug=363516](http://bugs.debian.org/cgi-bin/bugreport.cgi?bug=363516)

**Effectiveness of Static Code Analysis**

- They still require a significant level of expert knowledge
- Even for experts, analysis is still time-consuming
- Every little bit helps
- They can help find real bugs

Based: [51]

**For further reading**
Taken from [27]: “There are numerous good resources for readers interested in learning
more about automated code review and other software security technologies. Here are
the top five:”

- R. Anderson, Security Engineering: A Guide to Building Dependable Distributed Systems,
  2nd ed., John Wiley & Sons, 2008; [http://www.cl.cam.ac.uk/~rja14/book.html.](http://www.cl.cam.ac.uk/~rja14/book.html.)
- B. Chess and J. West, Secure Programming with Static Analysis, Addison- Wesley, 2007;
  [http://buildingsecurityin.com.](http://buildingsecurityin.com.)
- M. Howard and D. LeBlanc, Writing Secure Code, 2nd ed., Microsoft Press, 2003; [http:](http:)
  //blogs.msdn.com/michael_howard.
- G. McGraw, Software Security: Building Security In, Addison-Wesley, 2006; [http://www.swsec.](http://www.swsec.)
  com.
- J. Viega and G. McGraw, Building Secure Software: How to Avoid Security Problems the
  Right Way, Addison-Wesley, 2001; [http://www.buildingsecuresoftware.com.](http://www.buildingsecuresoftware.com.)

## 10.Testing

### 10.1. Introduction

**Testing**

**Developers vs. Testers**
“Good software developers will build systems that will accept or tolerate any data
whether it is non-conformant to the interface specification or just garbage.
Good tester, on the other hand, will subject the system to the most creative garbage
possible.” [46]

**Testing Goals**

- Finding bugs
- Ensuring that requirements are fulfilled
- Ensuring that changes don’t break existing code
- Tests should be repeatable

**Testing Targets**

- A single implementation
  **-** Method
  **-** Class
  **-** Application
- A specific device
- A complete system

**Testing Types**

- Testing
  Aimed at ensuring correctness of an implementation
  **-** Free of bugs
  **-** Fulfilment of requirements

##### 134

10. Testing IV Software Security for Autonomous Systems

```
Figure 10.1.: Testing. http://geek-and-poke.com/geekandpoke/2013/7/28/tdd
```

- Usability Testing

```
Mainly interested in the user experience
```

- Security Testing
  **-** Extension of previous testing activities with a security focus
  **-** Explicitly searching for vulnerabilities in software and/or the environment it
  is installed in

**V-Model**

**Testing vs. Security testing**

- Testing is concerned with bugs, errors, and failures
- Security Testing is concerned with a purposeful, malicious attacker

### 10.2. Security Testing

**Functional Security Testing/White-box Testing**

- Testers have access to source code and design documents
- Testers are
  **-** Developers: unit tests for security features

10. Testing IV Software Security for Autonomous Systems

Figure 10.2.: V-Model. [http://en.wikipedia.org/wiki/V-Model*(software*](http://en.wikipedia.org/wiki/V-Model*(software*)
development)

**-** Security experts: Create (automated) tests on unit, integration and system
level

**Black-box testing**

- Testers have no access to source code and design documents
  **-** Testers may not even have access to binaries, if applications can be tested
  over the network
- Manipulate input data → Create unintended application states/behaviour

**Tools & Techniques for Security Testing**

- Load Testing
- Stress Testing
- Security Scanners
- Unit Testing
- Fault Injection
  **-** Data fault injection
  **-** Code fault injection
- Syntax Testing
- Regression Testing

10. Testing IV Software Security for Autonomous Systems

**How to select good test data?**
“... good tester, on the other hand, will subject the system to the most creative
garbage possible.” [46]

- Boundary values
- Special values
- Random input

**Fuzzing**

- Try to smash programs with random input
- “Fuzzing is a black-box testing technique in which the system under test is stressed
  with unexpected inputs and data structures through external interfaces.” [46]

**Fuzzing - Approach (Simplified)**

- Test approaches based on fuzzing use
  **-** Unexpected inputs
  **-** Semi-valid inputs
  **-** Sequences of inputs
  Sends it to the interface that is tested

**Fuzzing – Goals**

- Find security-related defects, which may lead to
  **-** Denial of service
  **-** Degradation of service
  **-** Undesired behaviour
  “Fuzzing has one goal, and one goal only: to crash the system; to simulate a multitude
  of inputs aimed to find any reliability or robustness flaws in the software.” [Takanen 2008]
- If flaws are discovered: Can the flaws be exploited?

**Fuzzing and Vulnerabilities**

- Can help find unknown vulnerabilities:
  Static analysis tools require prior knowledge of vulnerabilities/programming errors
- Examples:
  **-** 25 out 30 tested Bluetooth implementations crashed [46]
  **-** 80% of tested products failed, products from the area of WAP, VoIP, LDAP,
  SNMP [46]

10. Testing IV Software Security for Autonomous Systems

**Fuzzing – Components of Fuzzing Testing Tools**

- Protocol Modeler
- Anomaly Library
- Attack Simulation Engine
- Runtime Analysis Engine
- Reporting

### 10.3. Test Documentation

**Testing Goals and Testing Documentation**
Goals:

- Testing should not be a random act
- Tests should be repeatable
- Test coverage should be measurable
  Consequences
- Tests need to be specified
- Test results need to be documented
- An overview of all tests should exist
  Goals and consequences are mainly relevant for manual tests, since automated tests
  partially already take care of this → meaningful names and code documentation (e.g.
  Javadoc) still important

**Test Plan**

- Lists all available tests
- May be used to bundle specification with similar environments → Reduce specifi-
  cation content
- Track the execution of tests and their outcome (success/failure)

**Test Plan Example**

- Use version control & version numbers
- Identify author & dates
- Identify testing area
- List tests

10. Testing IV Software Security for Autonomous Systems

Figure 10.4.: Test plan for the Common Criteria evaluation of the multiagent framework
JIAC IV. Consider this only to be an example and not a template. CVS
was used has a version control system and the example makes use of some
CVS features to version, dates, authors and ids to text files.

**Test Environment Example**

- Identify used hardware and relevant parameters for it
- Identify required software, version numbers and settings
- Identify the operation system with all relevant information

10. Testing IV Software Security for Autonomous Systems

Figure 10.5.: Test environment description for the Common Criteria evaluation of the
multiagent framework JIAC IV. Consider this only to be an example and
not a template.

**Test Specification**

- Defines pre-conditions for a test
- Defines the required environment
- Defines the goals and expected results of a test
- Defines the steps required to execute the test

**Test Specification Example**

- Use unique ids and names
- Include a link to the test plan
- Identify the software version of the tested artefact
- Describe the test objectives
- Describe pre-conditions for the test
- Describe the individual test steps

10. Testing IV Software Security for Autonomous Systems

Figure 10.6.: Test specification description for the Common Criteria evaluation of the
multiagent framework JIAC IV. Consider this only to be an example and
not a template.

```
Test ID : GUIUMT-12
------------------------------------------------------
Test plan : JIAC_IV_Cert_gui_usermanagement_TP.txt
------------------------------------------------------
Test name : ChangePasswordWithNonExistingUser
------------------------------------------------------
Tested version : JIAC 4.3.10
------------------------------------------------------
Attachments :
------------------------------------------------------
Test objective :
```

```
The objective of this test is to show that the login process of a
human user fails when using the service "changePassword" and not providing an
existing username for the authentication process. Therefore the service usage
will fail.
------------------------------------------------------
Needed preconditions :
```

```
It is required to open either, depending on the used operating system, a command
console (Windows) or a shell (Linux/Solaris). Then the tester has to open the
following directory: "JIAC-Certification\build\output\jiac-4.3.10Cert\bin".
```

```
In this directory a user account with the username and user password data pair
needs to be generated. This is either done under Windows with the command
"PwdManager -a user password01" and respectively under Linux/Solaris with the
command "./PwdManager.sh -a user password01". This will generate a user account
with the specified name: "user" and the appropriate password: "password01".
Note that this will only work correctly if the file ’users.pwd’, located within t
```

10. Testing IV Software Security for Autonomous Systems

```
he directory ’JIAC-Certification\build\output\jiac-4.3.10Cert’ was deleted.
Also the key of the ams-Seller platform must be copied to the key-partition.
```

```
During testing access to a running LDAP server must be ensured.
------------------------------------------------------
Test actions :
```

1. First of all the certification scenario needs to be started. For this there
   are four platforms needed. These platforms are all located within the
   following directory: "JIAC-Certification\build\output\jiac-4.3.10Cert\bin":
   i) The Trading_CA-Plattform can be started either with
   "trading_ca.bat" for Windows and respectively with "./trading_ca.sh" for
   Linux/Solaris. Also the platform password ("daidaidai") needs to be
   provided. In the following window a initial seed will be generated for the
   generation of good pseudo-random numbers. During this process the mouse
   needs to be moved within the generated seed window. After this the
   platform will be started.

```
ii) The process as described in i) needs to be repeated for the Trading_Seller-
Plattform, started by: "trading_seller.bat <key_partition>" under Windows and respectively
with "./trading_seller.sh <key_partition>" for Linux/Solaris. Again the passwords needs to
be provided ("daidaidai") for the following agents: seller.agent,
alterego.agent, and the dealer.agent. ben^tigt. After this the
platform and the agents will be started.
iii) The process as described in i) needs to be repeated for the
Trading_Producer-Plattform, started by: "trading_producer.bat" under
Windows and respectively with "./trading_producer.sh" for Linux/Solaris.
Again a password needs to be provided ("daidaidai") for the
producer.agent. After this the platform and the agent will be started.
```

```
iv) The process as described in i) needs to be repeated for the
Trading_Navigator-Plattform, started by: "trading_navigator.bat" under
Windows and respectively with "./trading_navigator.sh" for Linux/Solaris.
But here again first of all the seed will be generated. After this the
platform and the agent will be started.
```

2. Now in the navigator window the "Refresh" Button will be clicked. Following
   an overview over the provided services will appear in the window that is
   just positioned above.
   Now the service "changePassword" will be initiated by clicking the entry alterego
   under the changePassword service double-times.
   This will start this service in accordance with the appropriate entry for the
   "alterego" agent to use. Following in a new window the dialog for user
   authentication will appear.
3. Here the correct username ("user") and user password ("password01"), as
   generated before, data pair will be inserted and acknowledged with
   the OK button.
4. The window shall disappear and in the right half of the Navigator the window
   (GUI) for the service usage will be integrated. In the status bar of the
   Navigator the following message will appear:

```
Fri Jan 23 16:36:00 CET 2004: Requesting service changePassword with
provider alterego@ssl://arbeit.dai-lab.de:5555
```

10. Testing IV Software Security for Autonomous Systems

```
Fri Jan 23 16:37:22 CET 2004: Login process for service changePassword
successful.
```

5. In the service interface now the user provides a username that does not exist
   with any user password. Still the user tries to specify a new password. After
   acknowledging with the "OK" button the service interface will show the
   following message: "Service failed! Login process failed!". In the status bar
   of the Navigator the following error message will appear:
   Fri Jan 23 16:38:35 CET 2004: Login process failed.
   Fri Jan 23 16:38:44 CET 2004: Service completed successfully.

---

## Side effects :

## Miscellaneous :

**Test Protocol**

- Overview of all test results
- Date for tests
- Responsible testers
- Relates to a test specification
- Documentation of steps performed to execute a test
- Documents required outputs/results
- Documents the test outcome

**Test Protocol Example**

- Link to test specification
- Link to test plan
- Link to test environment
- Clearly identify outcome
- Include steps taken
- Link to issues resulting from test

www---------------------------------------------------
Test ID : GUIUMT-12

---

## Test plan : JIAC-IV_Cert_gui_usermanagement_TP.txt

10. Testing IV Software Security for Autonomous Systems

Figure 10.7.: Test specification description for the Common Criteria evaluation of the
multiagent framework JIAC IV. Consider this only to be an example and
not a template.

## Test specification : JIAC-IV_Cert_gui_usermanagement_Spez12.txt

## Test name : ChangePasswordWithNonExistingUser

## Tested version : JIAC 4.3.10

## Test environment : JIAC-IV_Cert_gui_usermanagement_Windows.txt

## Attachments :

## ++++++++++++++++++++++++++++++++++++++++++++++++++++++

Test result : Positiv
++++++++++++++++++++++++++++++++++++++++++++++++++++++

---

## Expected test result : Positiv

## Needed preconditions :

10. Testing IV Software Security for Autonomous Systems

Test action :

As described in the test specification - the commands
mentioned in step 1 were executed to start the platform.
Then the window was displayed, the "refresh" was clicked,
and the mentioned service was initiated whereupon the
user dialog was opened.

The following actions were executed according to the
specification in step 3 (login for service:
changePassword). The GUI for service usage was opened
as described in step 4. Finally the message, as specified
in step 5, was displayed.

The test was successfully accomplished.

---

## Side effects :

## Error description (Nr.) :

## Taken measures :

Miscellaneous :
---------------------------------------------------xxx

**Test Result Overview**

Figure 10.8.: Test result overview for the Common Criteria evaluation of the multiagent
framework JIAC IV. Consider this only to be an example and not a tem-
plate.

10. Testing IV Software Security for Autonomous Systems

**Testing and Common Criteria**

- Common Criteria: Standard to evaluate software security
- One part defines requirements on tests and test coverage
- Test plans, specifications and results are evaluated

**Automated Tests**

- Require similar documents, if not automatically generated
- For Java JUnit can be used and Javadoc for creating the test documentation
- Maven reports can be used as plans/result overviews

**An extended example: JIAC IV and SSL**

- Common Criteria evaluation for JIAC IV
- External communication must use SSL
  **-** JIAC node to JIAC node
  **-** UI to JIAC node
- Only one cipher suite allowed
- Mutual certificate exchange
- No critical X.509 certificate extensions
- Non-critical extensions will be ignored

**An extended example: JIAC IV and SSL – How & What**
How was this tested?

- Communication between nodes
- Communication between UI and node
- Tests repeated for Solaris, Linux and Windows

What was tested?

- Correct certificates and cipher suites
- Wrong cipher suites
- No certificate

10. Testing IV Software Security for Autonomous Systems
    - Expired certificate / Not yet valid certificated
    - Untrusted certificate issuer
    - Manipulated certificated
    - Wrong certificate chain

**An extended example: JIAC IV and SSL – What evaluators tested**

- Linux
- OpenSSL to JIAC Node
- More or less the same cases as we did, but Certificate chain validation tests failed
- OpenSSL encoded chains in PEM format, we used DER encoding → SSL library
  returned differently ordered chains depending on encoding

### 10.4. Penetration Testing

**What is Penetration Testing?**
“... refers to testing the security of a computer system and/or software application by
attempting to compromise its security, and in particular the security of the underlying
operating system and network component configurations.” [49]
“When applied to security testing, where the lack of a security vulnerability is the
negative we’re interested in, this means that **passing a software penetration test
provides very little assurance that an application is immune to attack** .” [3]

**Issues with Penetration Testing & SDLC**

- Usually applied at the end of the SDLC
- Mostly restricted to a limited time window
- **Problem** : Too little and to late, especially if it is the only security testing

```
Challenges
```

- Legality of conducted attacks
  Live system or test system?
  **-** How closely related/similar are test and live system?
  Testing during normal operation hours or outside this time window?
  **-** Realistic test conditions, if outside the time window?
  **-** Negative business impact, if during time window?

10. Testing IV Software Security for Autonomous Systems

**Useful because**

- Most closely related to actual attacker behaviour
- May help to find missed test cases and lead to additional security tests
- May help to find incomplete test cases or hidden environment dependencies

**What is needed**

- Clearly defined goals and metric to determine the outcome: Requires a security
  policy for the target
- Documentation of conducted tests and results
- Results should be reproducible

## 11.Operation

### 11.1. Introduction

**The road so far...**

- Requirements: Security Use Cases, Misuse Cases, Attack Pattern
- Design: Design Pattern, Software Security Pattern, System Security Pattern
- Development
- Testing

```
Software has been developed, know it is time to deploy and use it in a secure fashion!
```

### 11.2. Operations

**Operations**
“... If you’re a software author, all the good work that you’ve been doing – securely
developing and implementing your application – could be wasted if you don’t take time
to ensure that you’re deploying the application in a secure environment.” [16]

**Deployment and Operation – What is it about?**

- “How do I decide what to do and in what order?
- How do I do it?
- How do I know if what I did worked?
- How do I decide what to do next?” [2]

**What can go wrong**
“ _We didn’t install the [Code Red] patch on those DMZ systems because they were only
used for development and testing._

- Anonymous client, shortly after spending roughly 48 continuous hours removing
  2001’s Code Red worm from internal corporate servers” [16]

##### 149

11. Operation IV Software Security for Autonomous Systems

**Where can something go wrong?**

```
Figure 11.1.: Where can something go wrong?. Based on [16]
```

**Layers of security and operation practices**

```
Figure 11.2.: Layers of security and operation practices. Based on [16]
```

**Securing the network**

- Allow essential network services only: Requires careful network design/segmenta-
  tion, so that only application services/protocols are available in a network segment[16]
- Make use of secure network protocols: The best application security protocols are
  no help, if telnet is used for management access to a server running an application[16]

11. Operation IV Software Security for Autonomous Systems
    - Separate data from management: One network segment is used for production
      data exchange, another for management or backup[16]
    - Monitor for unauthorized activity: For example monitor any attempts to access the
      production data network segment, with protocols not belonging to the application
      → this could be an attack or a misconfiguration [16]
    - Deploy multiple layers of security
    - Log network events

**Event Logging Principles**

- Dedicated log server “Send event logs to a dedicated log server.”[16]
- Harden the log server “Harden the log server extensively and only allow access to
  it to staff who need to be on it.” [16]
- Use isolated management-only LAN/VLAN “Send event logs across an isolated
  management-only LAN or VLAN segment, preferably using an encrypted network
  protocol.” [16]

**Securing the operating system**

- Start with a secure baseline: Configure one system in detail, pay attention to all
  security configuration options, e.g. use guidelines for hardening the OS. Use this
  a template for other installations → restricts freedom of users and makes “bring
  you own device” impossible.[16]
- Make good use of file access control: Make use and study access control capabilities
  for the file system. Follow a least privilege approach for files, e.g. configuration
  files can only be read by the application and are invisible to users.[16]
- Allow essential network services only: Limit services and network interfaces to
  the ones needed for the application and maintenance. Also restrict availability of
  services to the counterparts that need it, e.g. only a front-end server is allowed to
  access a database. This should not only be enforced by the OS-level, but also by
  all network devices (router, firewalls,... ) → supports defense in depth[16]
- Remove what is not essential: Remove any network component or system tool that
  is not needed→ hardening the system (turn the machine into a bastion host): Not
  only disable service, components but completely remove them, e.g. FTP, compilers
  ... This prohibits an attacker from activating and using them[16]
- Install all current security patches: This can be work intensive, because production
  systems should not automatically install patches. Each patch/change to a system
  needs to be carefully checked before it is applied to the system. Otherwise this
  may lead undesired consequences and service interruptions. [16]

11. Operation IV Software Security for Autonomous Systems
    - Log operating system events: Log as many operating systems events as possible,
      to this in a secure manner and send them to a secure log server. In an research
      project we used such data from a data center to predict failures of the system,
      which in at least one case detected an upcoming failure a few hours before it
      actually occurred.[16]

**Deploy the application with due care**

- Make appropriate use of file access control: As with the OS think carefully about
  the files in your application and who needs what kind of access to them. Again
  take care and use a least privilege approach.[16]
- If feasible, install in a compartmentalized environment: If an OS provides sup-
  port for compartments or virtualization, you should use to further isolate your
  application from the OS and the network.[16]
- Turn on event logging: After logging events from the network and the OS, also
  ensure that application events are logged. An administrators may need these events
  to investigate an intrusion detection system alarm.[16]
- Apply the same standards to third-party code: Understand every third-party ap-
  plication od library you are using. Secure equally or even better that your own
  application. Check that these applications/libraries don’t weaken your security
  settings, e.g. in the case of file permissions.[16]

.

**Ensure sound operations**

- Manage privileges: Study carefully the access control capabilities in your environ-
  ment, identify all roles in the system and what kind of rights they need, again
  consider only the least amount of needed rights. Investigate the feasibility of role-
  based access control.[16]
- Conduct operations tasks securely: The set of tools, protocols and procedures
  used in day-to-day activities in data center housing your application need to be
  secure, e.g. not using telnet to access remote machines. Otherwise the maintenance
  activities may be avenue of attack and way into your application.[16]
- Manage configurations: Systems and applications need to be kept up-to-date. This
  means that operations staff needs to stay informed about current updates and secu-
  rity patches→ test and install these patches, in [Graff 2003] report that operation
  staff tend to avoid installing security patches for critical/important systems in fear
  to break something in the running system → this attitude of course opens these
  systems to attacks. A countermeasure to this attitude is to have sound procedures
  in place, that describe how new update/patches are tested, introduced to a system
  and how role-back to a previous version can take place.[16]

11. Operation IV Software Security for Autonomous Systems
    - Keep up to date with patches: As mentioned previously, it is important to keep
      up to date with patches, that means that everything related in managing configu-
      rations needs to happen in a timely fashion.[16]
    - Manage users and accounts: Users and accounts needs to be synchronized, e.g.
      database users and system account users, this means not only permissions, but
      also deactivation of accounts. Furthermore, this needs to be synchronized with the
      human resource department, so that employees are removed from the system when
      they leave the company, or the permissions are changed when the take over a new
      position/role in the company.[16]
    - Treat temporary employees and contract workers appropriately: The previous
      point is especially important for users/accounts that belong to temporary em-
      ployees and contract workers. The status information needs to be kept up-to-date
      rigorously and sharing generic accounts should be avoided.[16]
    - Test your configuration: Applying patches/security updates and fixing bugs in your
      application is important. But this should never happen without testing changes
      to a production system in a dedicated test environment, because you never know
      how a directly applied patch to the production system affects this system. [16]
    - Set up checks and balances: A clear separation of responsibilities should exist
      between, developers, testers and operations staff, but be careful to have working
      information exchange between the groups. [16]
    - Conduct (tape) backups securely: Besides the existence of a backup process is
      it also important to ensure that the backed-up data is secure from unauthorized
      access, e.g. by encrypting the data. Also a physical separation of backed-up data
      from the data center is required, in case of fires etc. Finally, it needs to be verified
      that a backup process actually allows to restore the data/system. [16]
    - Keep your incident response plan ready: In case of incidents a clear plan should
      exist, that details what steps need to be taken to investigate an incident and how
      to conduct repairs. For this every person/role needs to know its responsibilities,
      this should be achieved by regular exercises. Also it needs to be considered were to
      focus needs/should lie, either on restoration of business operations or on security
      evidence for legal proceedings.[16]

**Other good practices**

- Undertake threat and risk analyses: Prior to deployment and periodically after
  deployment conduct a threat analysis (Who is likely to attack the system? How
  likely is an attack?) and risk analysis (What is the business impact of the attack?)
  [16]

11. Operation IV Software Security for Autonomous Systems
    - Maintain currency: Everybody involved in design, development and operation
      should stay current in technologies related to the application. This includes also
      security technologies (offensive and defensive) and also means that also persons
      not directly involved with the security aspects of the application should have at
      least a current basic understanding of security technologies. [16]
    - Conduct periodic independent reviews: Conduct periodic system (security) reviews
      of your system. This should be done independently from the normal staff, to get
      unbiased review by fresh set of eyes. [16]
    - Monitor security events: Logging is not very useful, unless the log files are also
      regularly monitored/reviewed. Review intervals highly depend on the business
      value/criticality of an application. One problem in this regard of course is the
      amount of log data, they may require tool support in analyzing the log files. [16]
    - Consider intrusion detection systems: IDS are one approach to automatically ana-
      lyze log file, of course this too create log events/alarm messages. For this Security
      information and event management (SIEM) system may be used, there main pur-
      poses in this regard would be conduct alert correlation. [16]
    - Seek independent opinions: Besides having independent regular reviews of the
      system, it is also a good idea to include independent opinions during the other
      stages of the project. [16]

**Bad practices**

- Don’t pass the buck: Don’t assume someone else is handling security (e.g. devel-
  opers think operations staff is handling and vice versa).[16]
- Don’t let the developers rule the roost: Clearly separate developing, testing and
  operations → Prevent Developers from making unilateral decisions, the goal is to
  have a stable business application. [16]
- Don’t assume anything: Don’t simply assume that something is configured cor-
  rectly or works in a certain way, make sure you know those things. [16]
- Don’t use back doors: This may be an easy solution to be able to fix problems,
  but it also opens up attack vectors for attackers. [16]
- Avoid temporary fixes: “The problem that you fix badly today may well grow to
  be much more serious tomorrow.” → Make sure that a fix for an error message
  does not create problems in other parts of the systems or weakens security. [16]
- Avoid shortcuts: Avoid things that may seem simpler or faster, but are less secure
  or robust than a more complex solution. [16]

11. Operation IV Software Security for Autonomous Systems
    - Abandon the castle and moat mentality: Don’t think that you can use less secure
      practices or protocols, because your system is very isolated, e.g. in small network
      segment. [16]
    - Beware of mission creep: Take care when changing firewall rule sets, check if they
      violate an underlying security architecture or principles and are really needed. [16]
    - Don’t blindly trust third-party software installations: Make sure that any library,
      application or service you use/install, is actually working in a secure manner and
      does not conflict with your security policies/requirements[16]

**Case 2: Due diligence review from hell - The Application**

- Companies business: Providing network-based application service to customers
- The application:
  **-** Running on servers in a data center
  **-** 24x7 servers received information from external sources
  **-** Process information and format it
  **-** Delivered information to customers
- Application was developed internally by the company

**Case 2: Due diligence review from hell - Development Process**

- No formal or informal process for configuration and change management
- Developers compiled software on their PCs and directly deployed it to production
  servers
- No tracking of changes to the software

11. Operation IV Software Security for Autonomous Systems

**Case 2: Due diligence review from hell - Network Part I**

```
Figure 11.3.: Case 2: Due diligence review from hell - Network Part I. Based on [16]
```

**Case 2: Due diligence review from hell - Network Part II**

```
Figure 11.4.: Case 2: Due diligence review from hell - Network Part II. Based on [16]
```

**Testing at operations time**

- Use runtime checkers: Intercept calls from the application to the operating sys-
  tem and check for potential problems, e.g. buffer overflows → ntroduce a high
  performance overhead[16]
- Use Profilers: Build a statistical profile for application behavior, calls the appli-
  cation is making to the operating system or libraries. Check for anomalies in the
  behavior profile, they may indicated attacks/security problems.[16]

11. Operation IV Software Security for Autonomous Systems
    - Include penetration testing in the QA cycle: Conduct penetration testing before
      the application is finally deployed/available.[16]
    - Use black-box testing or fault-injection tools: Besides network/OS-related pene-
      tration tests, testing a deployed application should also include fuzzing or other
      fault injection approaches.[16]
    - Use a network protocol analyzer: Use protocol analyzers to debug the network-
      related part of your application, e.g. to verify that passwords are actually en-
      crypted during transport or that relevant portions of an XML document are
      encrypted.[16]
    - Take advantage of intrusion detection systems: Create IDS rules that are specific
      to your application.[16]
    - Do open source monitoring: Monitor public information sources for security related
      information relevant to your application: 1. Hacker forums that discuss vulnerabil-
      ities. 2. Vendor announcements related to vulnerabilities. 3. Material (e.g. design
      notes) that are published and are related to your application and could impact
      security. 4. Discussion forums that discuss threats against technologies, industries
      or nations.[16]
    - Use checklists: Have a checklist for following security mechanism at runtime, e.g.
      developers can inform operation staff of important security related log and audit
      files/messages.[16]

**Metric Data Collection**

Figure 11.5.: Metric data for the API gateway used the in the vulnerability assessment
solution developed by DAI Lab and used at DAI-Lab. It monitors all
deployed pre-production and production instances.

11. Operation IV Software Security for Autonomous Systems

**Anomaly (Fault) Detection in Metric Data**

Figure 11.6.: Anomlay detection can be used to predict fault in data centre servers. The
picture above originated in the DAI Lab research project ADEWaS. Here
various performance parameters were used to monitor the health of data
center servers. A time series algorithm computed a confidence interval in
which normal measurements are expected. Circled data point are located
outside this interval and good be an indicator of upcoming server failures,
especially if many of them occur or they have a high severity score.

**Short Summary – Operation and Deployment**

- Safely set-up the application
- Secure operation environment
- Running application and environment securely

```
This includes:
```

- Fine-tuning access controls
  **-** network
  **-** operating system
- Event logging and monitoring

“Put bluntly, operations organizations have put up with some rather stinky soft- ware
for a long time, which has made them wary. If we can set that argument aside for a
moment and look at the broader picture—that is, safely setting up the application in a
secure operational environment and running it accordingly— then the work that needs
doing can certainly be positively affected by information security. The best opportunities
exist in fine-tuning access controls at the network and operating system levels, as well as

11. Operation IV Software Security for Autonomous Systems

in configuring an event-logging and monitoring mechanism that’s most effective during
incident response operations. Attacks will happen, so be prepared to clean up the mess
afterwards.” [50]

## 12.Common Criteria

### 12.1. Introduction

**How Secure is Your Application?**

- Applications are either used in-house or by customers
- Customers may have an interest in assurance guarantees in regard to security
- Security assurances may be required for certain products, e.g. smart meter gate-
  ways

**Common Criteria**

- A set of common guidelines and criteria for evaluation security in products
  **-** Software
  **-** Hardware

**Overall goals**

- Establish trust in a product
- Create transparency in regard to security mechanisms
- Provide a commonly recognized security label for products
- Provide unified criteria and processes for security evaluation

**General approach**

- Select product for certification
- Select **protection profile** or define a unique **security target**
- Submit product, documentation and tests for evaluation and certification

**Security Target** Relates to a single product, is always required and may build upon a
protection profile

**Protection Profile** Defines security goals for a class of products, e.g. operating system,
smart metering gateway...

##### 160

12. Common Criteria IV Software Security for Autonomous Systems

Figure 12.1.: Short history of national approaches to security evaluation until common
internation recognized approach was standardized.

**What will be evaluated?**

- Your unique product
- The actual target of evaluation (ToE)
- The development environment
- Guidelines and process in your company and the adherence to those by employees

12. Common Criteria IV Software Security for Autonomous Systems

**Roles and Evaluation Workflow**

Figure 12.2.: Common criteria evaluations involve three a national certification agency, a
company performing the common criteria evaluation and the manufacturer
of the product.

**Example Multiagent Systems - Overview**

Figure 12.3.: A typical multiagent scenario with mobile agents that can move between
agent execution environments (nodes).

12. Common Criteria IV Software Security for Autonomous Systems

**Example Multiagent Systems - MAS and Threats**

```
Figure 12.4.: Typical targets and potential malicious MAS components.
```

**Example Multiagent Systems - JIAC ToE**

```
Figure 12.5.: The ToE for the JIAC common criteria evaluation.
```

12. Common Criteria IV Software Security for Autonomous Systems

**Example Multiagent Systems - JIAC SecurityTarget - SSL**

Figure 12.6.: Specification for cryptographic operation in the JIAC security target for
using SSL.

**Example Multiagent Systems - JIAC SecurityTarget - Key Management**

Figure 12.7.: Overview description for key management section in the JIAC security
target.

## 13.Self-Adaptive Systems

### 13.1. Introduction

#### 13.1.1. Definition

**Self-protection**
“An autonomic system protects itself from malicious attacks but also from end users
who inadvertently make software changes, for example, by deleting an important file.
The system autonomously tunes itself to achieve security, privacy and data protection.
Security is an important aspect of self-protection, not just in software, but also in hard-
ware (e.g., TCPA). A system may also be able to anticipate security breaches and prevent
them from occurring in the first place. Thus, the autonomic system exhibits proactive
features.” [18]

#### 13.1.2. Requirements

**Protection against attacks and changes**
“... protects itself from malicious attacks but also from end users who inadvertently
make software changes”

- Needs to be able to detect attacks, options are
  **-** intrusion/anomaly detection components
  **-** processing alerts from other IDS, anti-virus or firewall products
- Needs to be able to select countermeasures and assess impact and effectiveness

**Security, privacy and data protection tuning**
“... autonomously tunes itself to achieve security, privacy and data protection”

- Formal (machine interpretable) description of security, privacy and data protection
  **-** goals
  **-** policies
  **-** states
- Reasoning about current state in relation to goals
- Planning capabilities in order to tune the system, again impact and effectiveness
  assessment needed

##### 165

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

```
Threat Forecasting
“... anticipate security breaches and prevent them from occurring... ”
```

- Identification of vulnerabilities (software, hardware, configuration... ) assessment
  of impact and risks
- Modelling of attackers and defenders (skills, goals, resources,... )
- Forecast models
- Description of preventive measures, planning and impact/effectiveness assessment

#### 13.1.3. Example

```
Self-protection example: Banking
```

#### ✐

#### ✐

#### ✐

#### ✐

#### ✐

#### ✐

#### ✐

#### ✐

```
ASystematicSurveyofSelf-ProtectingSoftwareSystems 17:3
```

```
Fig. 1.Simpleonlinebankingsystemexample.
```

#### the application level [Langner 2011]. The Duqu worm, discovered in September 2011,

#### is a reconnaissance worm that does no harm to the infected systems but is tasked to

#### collect and exfiltrate information such as valid digital certificates that may be used

#### in future attacks. It further illustrates the deliberate and persistent nature of today’s

#### cyber threats [Bencsath et al. 2012]. ́

#### What has become increasingly clear from examples like these is that to protect to-

#### day’s software systems, especially those that are mission critical, applying static point

#### security solutions (e.g., firewall and one-time password authentication) is no longer

#### sufficient. Rather, there is a need for dynamic approaches that actively evaluate and

#### reassess the overall security posture of the entire system architecture.

#### From Within: Dynamic Architectural Behaviors. An equally pressing need for system

#### self-protection arises from the fact that software systems are increasingly designed to

#### take on more dynamic behaviors at runtime. As dynamic architectural styles (such as

#### service-orientation) become more widely adopted, a system function may, for example,

#### be reassembled and provisioned with different components (e.g., using Service Com-

#### ponent Architecture [Marino and Rowley 2010]). Similarly, a web service orchestrator

#### could be constructed to dynamically discover and access different service providers

#### (e.g., using a Business Process Execution Language (BPEL) engine). Runtime archi-

#### tectural changes like these tend to be security-relevant. For example, if a BPEL or-

#### chestrator switches a Partner Link from a non-responsive local service provider to an

#### alternative external provider, the new SOAP connection becomes an additional source

#### of vulnerability.

#### Therefore, as runtime system architectures become adaptive and dynamic, so must

#### their protection, as manual changes in security policies would simply be too slow and

#### too costly.

```
1.2. A Simple Motivating Example
```

#### Self-protection mechanisms for a software system can take many diverse forms. As an

#### example, let us suppose an intruder, through attempts such as phishing, has gained ac-

#### cess to an online banking system and starts to exfiltrate confidential user information.

#### Amuch-simplifiedarchitectureofthesystemisshowninFigure1.

#### Suppose shortly after the intruder breaks into the system, his access gets denied

#### and he can no longer gain access. To achieve this effect, the system could have taken

#### any of the following different measures.

#### —Therouter’sintrusiondetectioncapabilitydetectsthisintrusionatthenetworklevel

#### and automatically disables the connection from the source IP address.

```
ACM Transactions on Autonomous and Adaptive Systems, Vol. 8, No. 4, Article 17, Publication date: January 2014.
```

```
Figure 13.1.: “Simple online banking system example.”[54]
```

```
“Self-protection mechanisms for a software system can take many diverse forms. As
an example, let us suppose an intruder, through attempts such as phishing, has gained
access to an online banking system and starts to exfiltrate confidential user information.
A much-simplified architecture of the system is shown in 13.1. Suppose shortly after
the intruder breaks into the system, his access gets denied and he can no longer gain
access. To achieve this effect, the system could have taken any of the following different
measures.
```

- The router’s intrusion detection capability detects this intrusion at the network
  level and automatically disables the connection from the source IP address.
- The firewall detects unusually large data transfer that exceeds the predefined policy
  threshold and accordingly disables the HTTP connection.
- The ARchitecture Manager (ARM) monitors and protects the system by imple-
  menting the Monitor, Analyze, Plan, Execute (MAPE) loop for self-adaptation

```
WiSe 2025/2026 DAI-Lab/AOT, TU Berlin 166
```

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

```
[22]. Upon sensing an unusual data retrieval pattern from the Windows server, the
ARM shuts down the server and redirects all requests to a backup server accord-
ingly.
```

- Alternatively, the ARM deploys and manages multiple application server instances
  on the Windows machine. By comparing the behavior from all server instances
  (e.g., using a majority voting scheme), the ARM detects the anomaly from the
  compromised application server instance and consequently shuts it down.

While the first two examples merely execute predetermined actions using a particular
component, the latter two clearly exhibit self-adaptive and self-protecting be- havior at
the system level. As this article will show later, many other self-protecting mechanisms
are possible. How do these different approaches compare against one an- other? Are
some more effective than others? If so, under what conditions? To better answer these
questions, one must methodically evaluate the state of the art of the self- protection
approaches, architectures, and techniques, and assess how they address the externally-
driven and internally-driven security needs mentioned above.” [54]

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

#### 13.1.4. Reference Architecture

```
Self-protection reference architecture
```

✐

```
✐
```

```
✐
```

```
✐
```

✐

```
✐
```

```
✐
```

```
✐
```

```
ASystematicSurveyofSelf-ProtectingSoftwareSystems 17:5
```

```
Fig. 2. Self-Protection in light of FORMS reference architecture.
```

```
Figure 2 shows what we consider to be a self-protecting software system in light of
FORMS’ concepts. The meta-level subsystem is part of the software that is responsi-
ble for protecting (i.e., securing) the base-level subsystem. The meta-level subsystem
would be organized in the form of feedback control loop, such as the MAPE-K archi-
tecture depicted in the figure [Kephart and Chess 2003]. One should not interpret this
reference architecture to mean that the base level subsystem is agnostic to security
concerns. In fact, the base-level subsystem may incorporate various security mecha-
nisms, such as authentication, encryption, etc. It is only that the decision of when and
how those security mechanisms are employed that rests with the meta-level subsys-
tem. In the case of the online banking system introduced in Section 1.2, the banking
application logic corresponds to the base-level subsystem, while the logic used for de-
tecting an intruder and mitigating the threat through changes in the system corre-
sponds to the meta-level subsystem.
In addition to the intricate relationship between the meta-level and base-level sub-
systems, we make two additional observations. First, we underline the role of humans
in such systems. Security objectives often have to be specified by human stakeholders,
which are either the system’s users or engineers. As we will see in the remainder of
this paper, the objectives can take on many different forms (e.g., access control poli-
cies, anomaly thresholds). Second, we observe that for self-protection to be effective,
it needs to be able to observe the domain environment within which the software exe-
cutes. The domain environment is comprised of elements that could have an impact on
the base-level software, but are outside the realm of control exercised by the meta-level
subsystem. For instance, in the case of the online banking system, the domain could be
other banking systems, which could impact the security of the protected system, but
the meta-level subsystem has no control over them.
These concepts, although intuitive, have allowed us to define the scope of our study.
For instance, we were able to distinguish between an authentication algorithm that
periodically changes the key it uses for verifying the identities of users, and a system
that periodically changes the authentication algorithm it uses at runtime. The former
we classified to be an adaptive security algorithm, as the software used in provision-
ing security does not change, and therefore outside the scope of this article. While the
latter we classified to be a self-protecting software system, as it changes the software
elements used in provisioning security, and therefore within the scope of our study.
Though other reference frameworks exist (such as control theory-based DYNAMICO
[Villegas et al. 2013]), we will use the basic concepts introduced in this section through-
out the paper to illustrate the differences between the self-protection approaches
surveyed.
```

```
ACM Transactions on Autonomous and Adaptive Systems, Vol. 8, No. 4, Article 17, Publication date: January 2014.
```

```
Figure 13.2.: “Self-Protection in light of FORMS reference architecture.”[54]
```

```
FORMS reference model MAPE-K perspective FORMS: Unifying Reference Model for Formal Specification of Distributed Systems 8:9
```

```
Fig. 3. FORMS reference model derived from the unification of reflection and distribution perspectives with
those from the MAPE-K perspective.
feedback-control loop in self-adaptive software systems. On the other hand, MAPE-K is
neither formalized nor does it address some of the other important self-adaptation con-
cerns, such as those described in the previous two sections. Figure 3 shows the relation-
ship among FORMS primitives inspired by MAPE-K with the modeling primitives from
other perspectives. In the process of unifying the MAPE-K perspective with FORMS,
we identified several additional primitives that are not present in MAPE-K, including
arefinementofknowledge.TheMAPE-KperspectiveisdetailedinAppendixC.
As mentioned before, in FORMS a self-adaptive unit is a self-contained entity that
adapts the local managed system using several reflective computations , which use a
set of reflection models .TheMAPE-Kperspectiveallowsustodescribetheabstract
notions of reflective computation and reflection model more concretely.
In FORMS, we distinguish between four types of reflection models : subsystem model ,
concern model , environment model ,and MAPE working model.
Asubsystemmodelrepresents(partsof)the system that is managed by the self-
adaptive unit. The subsystem can be either a local managed system or a self-adaptive
unit. The latter is applicable to self-adaptive units that deal with higher-level concerns
(i.e., a metametalevel model). In the robotic application, the architectural models rep-
resenting the structure of the managed software system correspond to the subsystem
model.
Aconcernmodelrepresentstheobjectivesorgoalsofaself-adaptiveunit.Inthe
robotic system, for example, a self-healing concern can be represented as rules of the
form event – condition – action set. Event is a failure of a software component, condition
is a local dependency on the failing component, and action set comprises a set of repair
actions required to recover from the failure.
An environment model reifies the relevant part of the environment at the reflec-
tive level. In the robotic example an environment model may, for instance, represent
the physical environment, robot locations, and any other relevant environmental at-
tributes.
A MAPE working model represents runtime data shared between the reflective com-
putations. These models are typically domain-specific. Examples of working models in
aroboticsystemarethetemporaryrepresentationsofcandidatedeploymentarchitec-
tures for adapting the domain logic (i.e., the base-level subsystems).
ACM Transactions on Autonomous and Adaptive Systems, Vol. 7, No. 1, Article 8, Publication date: April 2012.
```

```
Figure 13.3.: “FORMS reference model derived from the unification of reflection and
distribution perspectives with those from the MAPE-K perspective.”[52]
```

```
Autonomic Computing & MAPE-K
```

- Inspired by the _autonomic_ behaviour in biological systems, e.g. the nervous sys-
  tems
- Aim: Self-management of IT components
- Main self properties:
  **-** Self-configuration
  **-** Self-optimization
  **-** Self-healing

```
WiSe 2025/2026 DAI-Lab/AOT, TU Berlin 168
```

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

```
44 Computer
```

```
interactions among autonomic elements as it will
from the internal self-management of the individual
autonomic elements—just as the social intelligence
of an ant colony arises largely from the interactions
among individual ants. A distributed, service-ori-
ented infrastructure will support autonomic ele-
ments and their interactions.
As Figure 2 shows, an autonomic element will
typically consist of one or more managed elements
coupled with a single autonomic manager that con-
trols and represents them. The managed element
will essentially be equivalent to what is found in
ordinary nonautonomic systems, although it can
be adapted to enable the autonomic manager to
monitor and control it. The managed element could
be a hardware resource, such as storage, a CPU, or
a printer, or a software resource, such as a data-
base, a directory service, or a large legacy system.
At the highest level, the managed element could
be an e-utility, an application service, or even an
individual business. The autonomic manager dis-
tinguishes the autonomic element from its nonau-
tonomic counterpart. By monitoring the managed
element and its external environment, and con-
structing and executing plans based on an analysis
```

```
of this information, the autonomic manager will
relieve humans of the responsibility of directly man-
aging the managed element.
Fully autonomic computing is likely to evolve as
designers gradually add increasingly sophisticated
autonomic managers to existing managed elements.
Ultimately, the distinction between the autonomic
manager and the managed element may become
merely conceptual rather than architectural, or it
may melt away—leaving fully integrated, auto-
nomic elements with well-defined behaviors and
interfaces, but also with few constraints on their
internal structure.
Each autonomic element will be responsible for
managing its own internal state and behavior and
for managing its interactions with an environment
that consists largely of signals and messages from
other elements and the external world. An element’s
internal behavior and its relationships with other
elements will be driven by goals that its designer
has embedded in it, by other elements that have
authority over it, or by subcontracts to peer ele-
ments with its tacit or explicit consent. The element
may require assistance from other elements to
achieve its goals. If so, it will be responsible for
obtaining necessary resources from other elements
and for dealing with exception cases, such as the
failure of a required resource.
Autonomic elements will function at many levels,
from individual computing components such as
disk drives to small-scale computing systems such
as workstations or servers to entire automated
enterprises in the largest autonomic system of all—
the global economy.
At the lower levels, an autonomic element’s range
of internal behaviors and relationships with other
elements, and the set of elements with which it can
interact, may be relatively limited and hard-coded.
Particularly at the level of individual components,
well-established techniques—many of which fall
under the rubric of fault tolerance—have led to the
development of elements that rarely fail, which is
one important aspect of being autonomic. Decades
of developing fault-tolerance techniques have pro-
duced such engineering feats as the IBM zSeries
servers, which have a mean time to failure of sev-
eral decades.
At the higher levels, fixed behaviors, connections,
and relationships will give way to increased
dynamism and flexibility. All these aspects of auto-
nomic elements will be expressed in more high-
level, goal-oriented terms, leaving the elements
themselves with the responsibility for resolving the
details on the fly.
```

```
Autonomic manager
```

```
Knowledge
```

```
Managed element
```

```
Analyze Plan
```

```
Monitor Execute
```

```
Figure 2. Structure of an autonomic element. Elements interact with other
elements and with human programmers via their autonomic managers.
```

```
Authorized licensed use limited to: Technische Universitaet Berlin. Downloaded on October 25, 2008 at 06:49 from IEEE Xplore. Restrictions apply.
```

Figure 13.4.: “Structure of an autonomic element. Elements interact with other elements
and with human programmers via their autonomic managers.”[22]

**-** Self-protection

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

### 13.2. Taxonomy

#### 13.2.1. Taxonomy - “What”

**Self-protection taxonomy: Approach Positioning (“What”)**

**T1** Self-Protection Levels

**T2** Depths-of-Defense Layers

**T3** Protection Goals

**T4** Lifecycle Focus

**T5** Meta-Level Separation

```
✐
```

```
✐
✐
```

```
✐
```

```
✐
✐
```

```
✐
✐
```

```
ASystematicSurveyofSelf-ProtectingSoftwareSystems 17:9
```

```
Fig. 3. Proposed taxonomy for self-protection.
“Monitor & Detect” is the most basic level, indicating the protecting subsystem is
equipped with the capability to constantly monitor for security threats and detect
anomalous or harmful activities from normal system operations. The next level is
“Respond & Protect”, which indicates the subsystem’s ability to take action against
the detected attack or anomaly. This implies the protecting subsystem can, ideally in
an autonomous fashion, (a) characterize and understand the nature/type of the at-
tacks, and (b) deploy the proper countermeasures to mitigate the security threat and
maintain normal system operations to the extent possible–apropertyoftencalled
“graceful degradation”. The third level, “Plan & Prevent”, represents the highest level
of sophistication; a security approach reaching this level allows a system to adapt and
strengthen its security posture based on past events so that known security faults are
prevented. We illustrate this dimension using the motivating example of Section 1.2.
—The online banking system is at the Monitor & Detect level if it is equipped with
anetworkbasedIDSdeviceconnectedtotherouter,whichcandetectanintrusion
ACM Transactions on Autonomous and Adaptive Systems, Vol. 8, No. 4, Article 17, Publication date: January 2014.
```

```
Figure 13.5.: “Proposed taxonomy for self-protection”[54]
```

WiSe 2025/2026 DAI-Lab/AOT, TU Berlin 170

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

```
Self-protection taxonomy: Approach Characterization (“How”)
```

```
T6 Theoretical Foundations
```

```
T7 Meta-Level Deicision-Making
```

```
T8 Control Topology
```

```
T9 Response timing
```

```
T10 Enforcement Locals
```

```
T11 Self-Protection Patterns
```

- Behavioural Patterns
- Structural Patterns

✐

```
✐
✐
```

```
✐
```

✐

```
✐
```

```
✐
✐
```

```
ASystematicSurveyofSelf-ProtectingSoftwareSystems 17:9
```

```
Fig. 3. Proposed taxonomy for self-protection.
“Monitor & Detect” is the most basic level, indicating the protecting subsystem is
equipped with the capability to constantly monitor for security threats and detect
anomalous or harmful activities from normal system operations. The next level is
“Respond & Protect”, which indicates the subsystem’s ability to take action against
the detected attack or anomaly. This implies the protecting subsystem can, ideally in
an autonomous fashion, (a) characterize and understand the nature/type of the at-
tacks, and (b) deploy the proper countermeasures to mitigate the security threat and
maintain normal system operations to the extent possible–apropertyoftencalled
“graceful degradation”. The third level, “Plan & Prevent”, represents the highest level
of sophistication; a security approach reaching this level allows a system to adapt and
strengthen its security posture based on past events so that known security faults are
prevented. We illustrate this dimension using the motivating example of Section 1.2.
—The online banking system is at the Monitor & Detect level if it is equipped with
anetworkbasedIDSdeviceconnectedtotherouter,whichcandetectanintrusion
ACM Transactions on Autonomous and Adaptive Systems, Vol. 8, No. 4, Article 17, Publication date: January 2014.
```

```
Figure 13.6.: “Proposed taxonomy for self-protection”[54]
```

```
A note Self-protection Patterns
Structural Patterns [54]:
```

- Protective Wrapper – “Place a security enforcement proxy, wrapper, or container
  around the protected resource, so that request to/response from the resource may
  be monitored and sanitized in a manner transparent to the resource.”

```
WiSe 2025/2026 DAI-Lab/AOT, TU Berlin 171
```

13. Self-Adaptive Systems IV Software Security for Autonomous Systems - Agreement-based Redundancy – “In addition to reliability and availability benefits
    provided by the common redundancy mechanism, this pattern uses agreement-
    based protocols among the replicas to detect anomalies and ensure correctness of
    results.” - Implementation Diversity – “IDeploy different implementations for the same soft-
    ware specification, in the hope that attacks to one impl. may not affect others.
    This may be achieved through the use of diverse prog. languages, OS, or H/W
    platforms. To safely switch requests from one instance to another, checkpointing
    is necessary to save the current system state.” - Countermeasure Broker – ‘The self-protecting system includes a brokering func-
    tion that, based on the type of an attack, performs dynamic matching or tradeoff
    analysis to select the most appropriate response or countermea- sure, often from a
    predefined repository.” - Aspect-Orientation – “Following the Aspect Oriented Programming (AOP) prin-
    ciple, this pattern deploys self- protection mechanisms as a separate aspect in the
    system, transparent to application logic. This pattern is often assisted by Model
    Driven Engineering (MDE) techniques... ”
    Behavioral Patterns [54]:

- Protective Recomposition – “Dynamically adapt security behavior of a system
  through altering how security-enforcing components are connected and orches-
  trated. This may include tuning of security parameters, changing authentication/
  authorization methods, switching to a different crypto algorithm, or regeneration
  of access control policies.”
- Attack Containment – “A simple pattern that seeks to isolate a compromised
  component from the rest of the system to minimize the damage. Typical techniques
  include blocking access, denying request, deactivating user logins, and shutting
  down the system component.”
- Software Rejuvenation – “... this pattern involves gracefully terminating an ap-
  plication and immediately restarting it at a clean internal state. Often done proac-
  tively and periodically.”
- Reconfiguration on Reflex – “A bio-inspired pattern that reconfigures the system
  to a higher level of protection (which may be more resource consuming), and when
  attack passes, returns to a less restrictive mode.”
- Artificial Immunization – “Inspired by adaptive immune systems in vertebrates,
  this pattern seeks to capture samples of worms or viruses; analyze the virus to
  derive a signature that can be used to detect and remove it from infected resources;
  and disseminate the ‘antidote’ to all vulnerable systems.”
- These patterns are not described in a formal manner like typical design patterns

13. Self-Adaptive Systems IV Software Security for Autonomous Systems
    - Only presented as a table
    - More like common ideas to solve a problem, than blueprints for a solution

### 13.3. Introduction

#### 13.3.1. Definition

**Self-healing**
“An autonomic computing system detects and diagnoses problems. The kinds of
problems that are detected can be interpreted broadly: they can be as low- level as
bit-errors in a memory chip (hardware failure) or as high-level as an erroneous entry
in a directory service (software problem).... If possible, it should attempt to fix the
problem, for example by switching to a redundant component or by downloading and
installing software updates. However, it is important that as a result of the healing
process the system is not further harmed, for example by the introduction of new bugs
or the loss of vital system settings. Fault-tolerance is an important aspect of self-healing.
That is, an autonomic system is said to be reactive to failures or early signs of a possible
failure.” [18]

**Self-healing vs. Self-protection**

- Self-healing addresses problems that occur **unintentionally** , e.g. faults in hard-
  ware/software
- Self-protection addresses **intentional** attacks
- Both try to prevent and recover the system from undesirable states and return it
  to “normal” states
- Actions should be taken with no or only with limited human intervention and
  control
- Organic computing [34] is a research direction which also looks at self-\* properties,
  but allows for higher degree of human involvement

### 13.4. Conclusion and Outlook

**If you want to learn more...**

- This lecture covered fundamental aspects of security and software engineering
- These of course also apply to autonomous of self-adaptive systems
- More detailed coverage on autonomous and AI approaches to cybersecurity are
  presented in other lectures at DAI/AOT

13. Self-Adaptive Systems IV Software Security for Autonomous Systems

**DAI/AOT Security Modules**

Figure 13.7.: DAI Lab/AOT provides four security lecture modules. _Software security_
contains _IV security aspects in software engineering_ and provides an intro-
duction to security aspects in software engineering and links this research
conducted at DAI Lab. _AI and Cybersecurity_ and the IV by the same ti-
tle introduce how AI concepts can be used for cybersecurity, this is mainly
discussed for the area of intrusion detection. _Intelligent Cybersecurity Solu-
tions_ and the project by the same name allows students to apply knowledge
gained from the two previous modules during the development of cybersecu-
rity solution, typically this will be some task related to anomaly detection.
Each semester the seminar _Autonomous Security_ is offered. Here students
work on individual presentations and reports for current research topics in
the area of cybersecurity.

## Part II.

# Lecture Example & Homework

# Assignments

##### 175

## 14.Lecture & tutorial example

Before diving into developing a self-adaptive cybersecurity monitoring solution, we start
with a short motivation for the example and technology choices.

### 14.1. Why Self-adaptive Cybersecurity Monitoring and Microservices?

A challenge for teaching developing AI-based applications is the lack of common devel-
opment frameworks/libraries. We have many choices for machine learning algorithms
but are lacking in distributed and intelligent applications. A common choice at AOT/-
DAI lectures is our own multiagent framework JIAC. A previous JIAC version (JIAC
IV) strongly focused on security, but unfortunately, the current versions did not focus
on security and standards. For this lecture, we chose instead of multiagent systems and
an approach based on microservices.

- Microservice-based solutions share aspects with multiagent systems:
  **-** Both are distributed and often reactive systems.
  **-** Both have a “language” to define communication between entities (agent or
  microservice).
  **-** Both encapsulate a specific focus and aim to fulfill a defined set of goals/tasks.
  **-** Creating an architecture for microservice or multiagent system solutions is
  similar.
- Microservices are currently a popular approach to developing software systems
- With microservices, we can also address technology solutions like Docker, Kuber-
  netes, REST, OAuth 2.0, and many more.

One difference between microservice and multiagent solutions is that microservices do
not contain AI-based components/aspects. But they can be used to realize self-adaptive
systems.

**Self-adaptive System & Autonomic Computing**

- Self-adaptive systems are a way to create software systems that exhibit autonomous
  behavior.

##### 176

14. Lecture & tutorial example IV Software Security for Autonomous Systems
    - A self-adaptive system can use the MAPE-K loop concept from Autonomic Com-
      puting to realize self-adaptive behavior.
    - A self-adaptive system clearly distinguishes between a managed and managing
      system
    - The managing system contains all components related to self-adaption and relevant
      artificial intelligence components
    - The managed system contains the actual domain/business logic

The homework will deal with an example scenario from the autonomous driving do-
main and the supporting roadside infrastructure. The development tasks will solely
focus on security aspects, and autonomous systems only provide a background story for
the tasks. To aid you in working on your assignment, we will discuss and develop a
cybersecurity solution that, at least in theory, also has autonomous behavior^1.

**Cybersecurity Monitoring**

- The example shows how autonomous systems can also be used for security pur-
  poses, and not only how they can be secured.
- Links our research at DAI Lab and our other security lectures.
- Can be realized as a software solution and does not require special hardware, many
  computing resources, or abstractions from the real world.

The proposed solution continuously monitors an IT environment and provides network
topology with all gathered details. If it discovers gaps in the monitoring, it develops
strategies to close them. In the example, we only look at how an authorized person can
fetch the network topology.

#### 14.1.1. Cybersecurity Monitoring and Security Requirements

The last paragraph in the previous was very short and not very detailed about what
the application should be done. In the following, we will gather in an informal way
requirements and security requirements for a cybersecurity monitoring solution.

```
Further reading
```

```
An in-depth discussion on how to approach requirements gathering and man-
agement in an organized fashion is out of the scope of this lecture. If you are
interested in more details, you can start with [6] or [42]. A good in-depth source
is [53].
```

(^1) The following article may provide some real-world background on what modern traf-
fic lights can do (but this is not part of the homework): https://www.heise.de/news/
Deutschlands-erste-KI-Ampel-regelt-in-Hamm-den-Verkehr-9215018.html.

14. Lecture & tutorial example IV Software Security for Autonomous Systems

```
Figure 14.1.: Use case diagram - Cybersecurity monintoring
```

So, how do we start with gathering our requirements? We identify who uses our
solution and for what purposes. Since UML provides us with use case diagrams, we
start with one.
The use case diagram in figure 14.1 is obviously only a quick idea^2 , but it is the
starting point we are going to use. In the diagram, we identified to _actors_. A _System
Administrator_ and a _Security Officer_. The system administrator wants to view what
elements exist in the network she is responsible for and what status these nodes have
(use case “View status of the network”), e.g. online or offline. Or she wants to review
if services are online or offline, which means we need information about services, open
ports, and installed software (including versions) for a node in the network. Maybe also
the information what about the type of a node, e.g. server or laptop. This is covered by
the use case “Review details of an asset.” From the wording of this use case, you also
can guess we see the nodes in a network as assets we want to protect. In the diagram, we
also have a second actor, the _Secrurity Officer_. He is not interested if nodes are online
or offline but if nodes are vulnerable or not. For vulnerable nodes, he also wants to see
what software products of configuration errors are the cause of the problem.
So, we can identify these general requirements:

1. A system administrator MUST be able to retrieve an overview of all elements in a
   network. This overview SHOULD include information if a node is online or offline
   and when it was last seen online.
2. A system administrator MUST be able to access detailed information about a node
   in a network. These details MUST include the operating system and operating
   system version for the node, open ports, installed software products, and versions

(^2) Your homework assignments should be more detailed.

14. Lecture & tutorial example IV Software Security for Autonomous Systems

```
for those products. The detailed information about nodes MAY include additional
details, e.g. node types.
```

3. A security officer MUST be able to identify vulnerable nodes in a network.
4. A security officer MUST be able to retrieve CVEs or other identifiers for vulnera-
   bilities on a node, and he MUST be able to link these identifiers to specific software
   products or other causes for a vulnerability.

```
Figure 14.2.: Misuse case diagram - Cybersecurity monintoring
```

In the misuse case diagram, see figure 14.2, we introduced a generic hacker. This
hacker wants to exploit a vulnerability in our IT environment, so he first needs to learn
what the environment looks like and what vulnerabilities exist.
This has actually nothing to do with our application, but unfortunately, our appli-
cation can make the life of the hacker easier. It already has all the information the
hacker wants, so instead of finding everything out by himself, he can simply steal the
information from us. These misuse cases can be realized by first using a technique called
“Gather Victim Identity Information”^3. With this, the adversary has identified potential
victims for a targeted phishing campaign for initial access^4. This could allow the hacker
to use our application and exfiltrate the desired information^5.
A few security requirements that could help mitigate the threat from the adversary:

1. The cybersecurity monitoring components that provide information about an IT
   environment MUST be isolated from the network it monitors.
2. Only authenticated and authorized users are allowed to access information from
   the cybersecurity monitoring component.

(^3) https://attack.mitre.org/techniques/T1589/
(^4) https://attack.mitre.org/techniques/T1566/
(^5) https://attack.mitre.org/techniques/T1567/ is one option for this

14. Lecture & tutorial example IV Software Security for Autonomous Systems 3. Only security officers in the company MUST be allowed to access information
    related to vulnerabilities. 4. General network topology information MUST be stored separately from vulnerability-
    related information.

```
Figure 14.3.: Security use case diagram - Cybersecurity monintoring
```

These requirements we summarize as security use case “restrict access” in figure 14.3.
This is not perfect, but for now, this keeps the diagram readable. The details will be
part of the design since access restrictions will be enforced at multiple locations and with
multiple approaches.

### 14.2. System Overview

We will not use UML to model our system but an alternative approach called C4^6.
Our system has two main components; see figure 14.4. An authorized administrator
uses a command line interface, and a microservice provides a network topology.

**System Context**
In figure 14.5, we have broken down these systems into containers^7. Since we have
a trivial system, breaking them into components is unnecessary. The containers will
already map to classes that we implement in the end. In an actual application, this will
be a bit more complex.

**Container Diagram**

(^6) https://c4model.com/
(^7) A bit overloading of terms here. Containers should not be confused with anything from Docker or
Kubernetes.

14. Lecture & tutorial example IV Software Security for Autonomous Systems

```
Security/System Administrator
Requires information about [Person]
network and manages the security monitoring
```

```
Command Line Interface [Software System]
An application that allows to manage the monitoring and allows to fetch
information. It is running as an application on an admin PC
```

```
[Software System] SACY
monitoring. Running in a Kubernetes Self-adaptive cybersecurity
cluster.
```

```
Person Software System External Software System
```

```
Self-adaptive Cybersecurity Monitoring The system context diagram for a self-adaptive system to monitor a network
```

```
applicationExecutes Query information monitoring& control
```

```
Figure 14.4.: A system context diagram for the implemented system.
```

```
Command Line Interface [Software System] SACY [Software System]
```

```
Security/System Administrator [Person]
Requires information about network and manages the security monitoring
```

```
applicationExecutes
```

```
[Container: Java and Quarkus Main] CLI Application
commands via REST client interfaces.Parses the commands and issues
```

```
[Container: Java and REST Client Ressource] Network Monitoring
Provides a REST client for interacting with SACY
```

```
[Container: Java and Quarkus REST Ressource] Client Resource
Demonstrates how a simple default Quarkus Web page can be used to connect to REST endpoints
```

```
Calls REST client Calls endpointsHTTP(S)/REST: Calls network monitoring methods
```

```
Calls REST client
```

```
Network Monitoring [Container: Java and Quarkus] Resource
REST endpoints for SACY network monitoring
Logic for the network monitoring Network Monitoring [Container: Java]
```

```
Figure 14.5.: Container diagram for the example implementation.
```

## 15.Homework Scenario: Microservice Architecture for Traffic Light Management

The lecture will be paralleled by homework tasks that build on each other. The focus of
every homework assignment should be on security issues or mechanisms. Non-security-
related aspects should be covered only at the bare minimum. So, for example, do not
bother implementing a scheduler to switch traffic lights; assume one exists, and (manual)
events will trigger the only actual state change.

### 15.1. Scenario introduction

The following begins with a brief background story that provides some flexibility in
design and development decisions in later sections.

#### 15.1.1. A short time ago in a town far, far away

The city council of Duckburg recently discovered the existence of the Internet (see figure
15.1). They learned that the Internet enables many interesting new possibilities.

Figure 15.1.: As it is known to all knowledgeable managers of IT crowds, it is a box to
house clouds.^1

The most exciting aspect was that the Internet could connect all things (the Internet
of Things—IoT), so it was decided that it would be a good idea to manage the city’s
traffic. Being a modern city, Duckburg had a lot of traffic, and everybody complained
about traffic congestion and always seeing red lights.

(^1) (Image source: Open Clip Art Library [http://www.publicdomainfiles.com/show_file.php?id=](http://www.publicdomainfiles.com/show_file.php?id=)
13932766815582 ), License: Public Domain (https://creativecommons.org/publicdomain/mark/1.
0/)
If you are interested in the story behind the picture, I suggest watching the fourth episode in season
3 of “The IT Crowd”.

##### 182

15. Homework Scenario IV Software Security for Autonomous Systems

Since Duckburg is a large town, it was recently discovered that there was insufficient
money to connect all its streets to the Internet. So it was decided to start with a pilot
project for the Main Street (Smart Autonomous Main Street (SAM)^2 ) and only connect
traffic lights on this street to the Internet. To realize the project, the city council of
Duckburg decided to contract the well-known IT company New Land Explorers LLC.
(NLE).

#### 15.1.2. Smart Autonomous Main Street - Project vision

After NLE was contracted and had several discussions with the city council of Duckburg,
it was decided that the SAM project would be conducted in several phases. In phase
one, a team of elite software engineers would focus on the intersection at Main Street
and First Avenue; see figure 15.2 for a typical intersection in Duckburg.

```
West East
```

```
North
```

```
South
```

```
Main Street
```

```
1st/2nd/3rd Avenue
```

Figure 15.2.: NLE is tasked to connect the traffic lights for the shown intersection of
Main Street and First Avenue to the Internet.

```
NLE senior management compiled the following list of customer demands:
```

- All traffic lights at an intersection shall be connected to the Internet and allow a
  traffic management center to control the traffic lights.
- The traffic management center should accurately show the current status of the
  traffic lights.

(^2) A short hint for your life as a researcher: For funded research projects, it is always good to have a
catchy acronym, sometimes it is even required to have an acronym that can be spoken and not only
spelled.

15. Homework Scenario IV Software Security for Autonomous Systems
    - Emergency vehicles (police cars, fire trucks, ambulances) should be able to request
      green lights from the traffic management center when they approach an intersec-
      tion.
    - The mayor of Duckburg will travel with an autonomous vehicle, the first and only
      one in Duckburg. Her autonomous car shall digitally receive traffic signals and
      be able to switch them to green when it computes that it otherwise has to stop
      at a red light. Other autonomous vehicles also receive the traffic status and may
      request a green light, but are denied. Also, the mayor agreed that emergency
      vehicles should be prioritized over her car.

#### 15.1.3. Your mission

Should you decide to enroll in IV Software Security for Autonomous Systems, your
mission is to be the elite team of software engineers to securely and safely connect
Duckburg’s traffic lights to a traffic control center in Duckburg City Hall. As always,
should you or any of your group not meet the expectations for a task, the lecturer
will award your homework task zero points (or a little bit more). This document will
self-destruct whenever the ISIS portal closes access to PDF files. Good luck.

#### 15.1.4. Technology constraints & general homework requirements

The following description follows Best Current Practices (BCP) 14^3 for using keywords
like MUST. Please keep this in mind when working on the homework assignments.

**Required technologies**

- Git **MUST** be used as a version control system.
- **Java MUST** be used as a programming language for the traffic control center,
  the traffic lights, and other components.
- For communication between the traffic control center and other parts of the system,
  a **REST API MUST** be used.
- For developing the (distributed) components of the system, a microservice archi-
  tecture approach **MUST** be used.
- For developing individual microservices Quarkus^4 **MUST** be used.
- The build and deployment process **MUST** be conducted with **Maven**. It **SHOULD**
  be completely automated, or it **MUST** document all manual steps required to
  build and deploy it.

(^3) https://tools.ietf.org/html/bcp14
(^4) https://quarkus.io

15. Homework Scenario IV Software Security for Autonomous Systems

**Grading relevant requirements**

The following requirements will impact the grading of homework submissions.

**General submission requirements**

- Each homework submission **MUST** contain a GitLab Wiki page with either the
  submission results (e.g., design documents) or a link to the source code release and
  a detailed description of how to build and use the submitted code.
- For any textual submission, you **MUST** follow correct procedures for referencing
  source material. Furthermore, any reference MUST use a correct citation format
  for the referenced format, e.g., a conference paper **MUST NOT** only be a URL
  to ResearchGate, but a correct reference for a conference paper.

```
Code submission requirements
```

- The TU Berlin GitLab server **MUST** also be used for all homework-related sub-
  missions. For this, you **MUST** use the GitLab projects provided to your group.
- Source code submissions **MUST** be a release of your project; use GitLab to create
  this release^5.
- Microservices **MUST** be deployed to a local Kubernetes cluster created with the
  scripts provided for the lecture example.
- Maven project artifact IDs and Kubernetes namespaces **MUST** include the group
  name.
- I must be able to compile your projects on the command line with the following
  commands:
  **-** mvn clean install - for building and testing everything.
  **-** mvn clean package -Dquarkus.kubernetes.deploy=true - for deploying
  all microservices.
  **-** Tests **MUST** not fail, especially due to externally needed services.

#### 15.1.5. Aspects that will impact the grading

- The constraints and requirements, see section 15.1.4 have been followed. If a
  **MUST** point was not considered, e.g., if a language other than Java was used for
  the implementation, the homework assignment will receive 0 points.
- Requirements and design **MUST** be specific to the described task, and **MUST**
  **NOT** be so abstract that they could match any application.

(^5) https://docs.gitlab.com/ee/user/project/releases/

15. Homework Scenario IV Software Security for Autonomous Systems
    - Code quality and general best coding practices will impact the grade for implementation-
      related tasks.
    - The provided documentation will be followed to the letter for building artifacts,
      executing tests, deploying, and using the software. If information is missing or
      incorrect, this may result in fewer or even no points.
    - Multiple client applications will be needed; their number may depend on your
      design and my feedback. One client will not be enough. Of course, code reuse
      between Maven projects is an option that you have.
    - For any method or REST API endpoint you **MUST NOT** use generic types like
      Object, JsonObject, or Response (these are only examples for what is considered
      to be a generic type; when in doubt, ask). If you are of the opinion that generic
      data types cannot be avoided, provide a reasoning for your decision, either as a
      comment in the source code or a text in your Wiki page for a submission (best
      with a link to the Wiki in the source code as a comment, in case I start with the
      source code).

#### 15.1.6. Aspects that will not impact the grading

- A fancy user interface is not required; providing only a command line/console
  interface is assumed. If you want to do something fancier, e.g., web-based UI, you
  may do so and also use languages other than Java for the UI. However, be aware
  that this may increase the effort required to solve access control tasks and create
  REST API clients.
- Client application **SHOULD NOT** run in the Kubernetes cluster.

### 15.2. Homework assignment overview

The following lists the tasks and provides a short description. Deadlines and points for
each task can be found in the corresponding task in ISIS.

#### Task 0: Set-up development environment

For work on the homework assignments, you need to install the following tools:

- Java, version 21 or higher
- Maven, version 3.9 or higher
- IDE for Java
- UML drawing tool (or C4 drawing tool)

15. Homework Scenario IV Software Security for Autonomous Systems
    - Docker^6 , Kind, kubectl, and Helm

After installing everything, try to start the local Kubernetes cluster provided to you. If
this works, review the lecture example, build it, and verify that everything is functioning
correctly. The second tutorial will be used to discuss and solve any issues. The cluster
and the example will serve as an environment where you must also deploy your own
solutions.

#### Task 1: PKI in Kubernetes for Microservices

For upcoming assignments, you will need to use TLS. In this task, you will create a PKI
with cert-manager^7 for an imaginary microservice and use trust-manager^8 to distribute
trust stores to microservices. So that microservices can get a private key and X.509
certificate for TLS, and know which CA X.509 certificates to trust.
**This task needs to be submitted by everyone and will be graded individu-
ally!**
What you need to do for this task:

- The example already contains a PKI and has generated a root CA, an intermedi-
  ate CA, and certificates for Microservices. You can re-use the root CA, but you
  should generate an intermediate CA that is a cluster issuer. The certificate for the
  intermediate CA **MUST** contain your group as an organization, the task as an
  organizational unit, and the common name should contain your name and identify
  this certificate as being a CA, e,g, CN=Karsten Bsufka CA. Deploy the interme-
  diate CA certificate to the default cert-manager namespace. **Important: The**
  **name used for the certificate resource should include the task, group,**
  **and your name. So that I can create all certificates without running**
  **into duplicate names.**
- Create a namespace for this task. Again, the namespace name **MUST** include the
  task, group, and your name.
- Create a certificate for an end entity, the certificate **MUST** contain an alternative
  name for a (hypothetical) service in the namespace you created, and can be resolved
  by microservices in other namespaces. Furthermore, also include the name of a
  potential ingress host in the certificate.
- The certificate **MUST** be issued by the intermediate CA you created.
- The certificate **MUST** be deployed as a secret to the namespace you created.
- Use kubectl to retrieve the secret, decode the contents for the certificates, and use
  openssl to show the certificates. Document this with the commands used and the
  generated output.

(^6) Podman may also work, and it comes already with Kind. But I have never tested this combination.
(^7) https://cert-manager.io/
(^8) https://cert-manager.io/docs/trust/trust-manager/

15. Homework Scenario IV Software Security for Autonomous Systems
    - Ensure that trust-manager stores the root CA as a config map or secret in the
      namespace for this task.

```
Submission deadlines are published in ISIS.
```

#### Task 2: Requirements, security requirements & high-level design

This task is worked on in a group and requires you to identify security requirements
and create a high-level design for the homework scenario. This should be used as a
foundation for your implementation tasks, and ideally also for test cases later on.

**Security requirements**

Based on this document, your task is to clarify what needs to be done. You should iden-
tify general requirements and security requirements^9. Essential for this task is WHAT
needs to be done and not HOW, but based on the technical constraints in section 15.1.4,
you may identify some requirements that are related to these constraints. Also, when
formulating the requirements, remember that they can be later used as test cases for
your implementation. This means you can show, based on successful test cases, that the
requirement has been satisfied.
For the requirements part of the task, the following is expected:

- Use case diagram(s) with a general overview of what the system is supposed to do.
- Misuse case diagram(s) with possible adversary activities and security use cases
  that could mitigate a misuse case. Please focus on things an adversary may try
  to achieve and consider likely adversaries, e.g., a disgruntled employee, a car race
  participant, or a terrorist from a _Die Hard_ movie,_..._.
- For one selected misuse case, an attack tree shall be created that links entries to
  attack patterns, either from CAPEC or Mitre ATT&CK.
- A list of requirements and security requirements derived from use case and misuse
  case diagrams. Note: It should later be possible to define tests for the conditions.
- For both the security requirements and misuse cases, please keep in mind or identify
  what is safety critical for traffic lights at an intersection. Also, consider not only
  cars and other vehicles, but also keep pedestrians in mind.

Since it is impossible to compile a complete list of requirements. You should have
between 10 - 20 requirements; for this, please focus on security requirements.

(^9) Some informal requirements were already listed in section 15.1.2

15. Homework Scenario IV Software Security for Autonomous Systems

**Architecture overview**

Based on the requirements, you should now start addressing the question of HOW to
solve the task. This may also lead to additional requirements.
Since the homework scenario describes a distributed system, this should be reflected
in the architecture overview. Note: For the actual implementation, you may need to
revise this with software security patterns from the lecture, but since this assignment is
due before that lecture is scheduled, at this point, you can still skip that aspect.
For this task, the following submissions are expected:

- UML diagram(s) for describing the components and identifying boundaries be-
  tween the distributed components.
- UML diagram(s) that identify how components will be later deployed. Remem-
  ber to model different physical locations with Kubernetes namespaces. The de-
  ployment diagram should reflect how you would deploy identified microservices or
  abstract components that will later be mapped to microservices.
- UML diagram(s) describing the dynamic behavior in the system, which should
  primarily address a traffic control center that gathers all status information and
  triggers state changes or state change requests sent from different vehicle clients/-
  types.
- Part of this task is to decide which UML diagrams are the most useful in your
  opinion.
- In the diagrams, make sure you use names and descriptions that are helpful for a
  potential developer. When in doubt, also provide additional documentation/de-
  scriptions to your diagram.

All content should be documented on a Wiki page in your GitLab project, and the
link to this page should be submitted to ISIS.

#### Task 3: Create and deploy microservices and create clients

This task should familiarize you with developing and deploying microservices. **Impor-
tant:** Functionality for the microservices and clients is not needed; just use hard-coded
values in REST API calls.
What is expected for this task:

- Based on the requirements and the design, you create and deploy microservices to
  a Kubernetes cluster.
- Based on the requirements and the design, you create client applications.
- Client applications and microservices use the REST API endpoints that you iden-
  tified in the requirements and the design.

15. Homework Scenario IV Software Security for Autonomous Systems
    - A detailed README file exists that describes how to build and use your clients
      and microservices.
    - You created a release for the homework assignment in GitLab and submitted a
      link to the release.

kubectl get pods -n keycloak

#### Task 5: Extend clients and microservices with access control

Continuing with the previous task, it is time to restrict access to your microservices.
The following is required for this task:

- Create a realm in Keycloak, and configure roles and clients.
- Restrict access to your microservice endpoints.
- Document how your security policy should look.
- A detailed README file exists that describes how to build and use your clients
  and microservices.
- You created a release for the homework assignment in GitLab and submitted a
  link to the release.

15. Homework Scenario IV Software Security for Autonomous Systems

#### Task 6: Add functionality and tests

So far, nothing really happened with your microservices. Now, you should add function-
ality to them and test the functionality and your security mechanisms.
There is a requirement in regard to data structures you must use: **Status Check
Service: Data structures**

**Traffic Light Id** This is used to identify a traffic light uniquely and should contain at
least the following information and meet the following constraints:

- A GPS position for the traffic light, with degree, minute and second^10. Since
  the traffic lights are located in Duckburg, it should only be possible to define
  locations within the city^11.
- A UUID for the traffic light.

**Traffic Status** Describes the current state of a traffic light and should contain at least
the following:

- An identification of the traffic light
- Time for the status information
- Status values

**Id and Status** Make a decision on how you represent the individual lights at an inter-
section.
For the task, the following is expected:

- An implementation for the data structures above.
- Unit tests that thoroughly test the data structures.
- Clients use the microservices to:
  **-** Query the status of an intersection (and all traffic lights).
  **-** Safely change the state of an intersection.
- Ensure that no unauthorized interactions are possible.
- A detailed README file exists that describes how to build and use your clients
  and microservices.
- You created a release for the homework assignment in GitLab and submitted a
  link to the release.
  Additionally, each group member must submit a test specification and a test protocol
  for a manual security test case. The manual test will be graded individually, and the
  rest will be graded for the group.

(^10) See https://en.wikipedia.org/wiki/Geographic_coordinate_system and https://en.wikipedia.
org/wiki/World_Geodetic_System#WGS84
(^11) Please try to avoid placing Duckburg somewhere in the Pacific Ocean; this may cause problems at
180 ◦E/W.

# Appendices

##### 192

## A. Quick references

```
I will provide an overview of relevant command-line commands you may need for the
homework assignments.
For more complete lists see:
```

- Kubernetes Quick Reference: https://kubernetes.io/docs/reference/kubectl/
  quick-reference/
- Docker CLI Cheat Sheet: https://docs.docker.com/get-started/docker_cheatsheet.
  pdf
- Quarkus Getting Started: https://quarkus.io/get-started/
- Maven Reference Card: https://maven.apache.org/guides/MavenQuickReferenceCard.
  pdf

### A.1. Kubernetes

```
Explain resource types Get some information about Kubernetes resource types, e.g.
kubectl explain pod
```

```
List resources Either list resources for all namespace with kubectl get pods -A, or
for a specific namespace, e.g. with kubectl get pods -n ingress-nginx.
```

```
Get log files kubectl logs -f hello-world-f5555ccc9-pfhbl -n iv-ssas-example
https://kubernetes.io/docs/reference/kubectl/generated/kubectl_logs/
```

```
Execute commands in a pod https://kubernetes.io/docs/reference/kubectl/generated/
kubectl_exec/
```

- kubectl exec hello-world-f5555ccc9-pfhbl -n iv-ssas-example – date
- kubectl exec –stdin –tty hello-world-f5555ccc9-pfhbl -n iv-ssas-example
  - /bin/bash

### A.2. Quarkus

```
Listing A.1: Create a Quarkus project
```

1 mvn i o. qua rku s. p l a t f o rm:qua rkus−maven−p l ug i n: $QUARKUS_VERSION:c r ea t e \
2 −Dpr o j e c tGr oupId=d e. tub. ao t \

##### 193

```
A. Quick references IV Software Security for Autonomous Systems
```

3 −Dpr o j e c tAr t i f ac t Id=he l lo−wo r l d \
4 −DnoCode \
5 −Dext ens i ons= ’ r e s t ’

## B. Installing cert-manager, Kyverno and trust-manager

```
The Kind cluster for the lecture^1 contains a script^2 for setting up a PKI in the Kind
Kubernetes cluster. In this chapter, we will discuss the main parts of the script.
```

Listing B.1: Use Helm to install cert-manager
1 kube c t l app l y−f ht tp s:// g i thub. com/c e rt−manage r/ c e rt−manage r/ r e l ea s e s/down
l oad/$CERT_MANAGER_CHART_VERSION/c e rt−manage r. c rd s. yaml
2
3 he lm upg r ade−−i ns t a l l c e rt−manage r j e t s t ack/c e rt−manage r \
4 −−name spac e c e rt−manage r \
5 −−ve r s i on $CERT_MANAGER_CHART_VERSION \
6 −−s e t s t a r tupap i che c k. enab l ed=f a l s e \
7 −−c r ea te−name spac e
8
9 kube c t l wa i t−−f or=cond i t i on=Ready pod \
10 −l app. kube rne t e s. i o/ i ns t ance=c e rt−manage r \
11 −n c e rt−manage r−−t imeout=300 s

```
Listing B.1 shows how we can use Helm to install cert-manager. But before in-
stalling cert-manager (lines 3-8), we must first install the custom resource definitions
cert-manager requires (lines 1-2). When the Helm chart for the cert-manager has been
deployed, we will wait for the cert-manager pods to be ready (lines 13-16).
```

```
Listing B.2: Create cluster and namespace issuer/CAs
1 kube c t l c r ea t e−f ${PKIDIR}/s e l f s i gned−r oot− i s sue r. yaml
2
3 kube c t l c r ea t e−f ${PKIDIR}/de f au l t−ca−c e r t. yaml−n c e rt−manage r
4 kube c t l c r ea t e−f ${PKIDIR}/ i vs s a s examp l e−ca−c e r t. yaml−n iv−s s as−examp l e
5
6 kube c t l c r ea t e−f ${PKIDIR}/de f au l t−ca−i s sue r. yaml−n c e rt−manage r
7 kube c t l c r ea t e−f ${PKIDIR}/ i vs s a s examp l e−ca−i s sue r. yaml−n iv−s s as−examp l e
The script excerpt in listing B.2 shows how to create issuers (CAs) in our cluster. In
line 1, we create a self-signed root issuer for our cluster.
It is important to note that two configuration files define a CA. One file defines a
(cluster) issuer resource, which will issue certificates later. The second file specifies the
contents of the certificate that belong to the issuer. But before looking at them, we must
```

(^1) https://git.tu-berlin.de/aot-security/iv-software-security-for-autonomous-systems/
iv-ssas-kind-cluster
(^2) 03-create-pki

##### 195

```
B. cert-manager IV Software Security for Autonomous Systems
```

```
create a self-signed root cluster issuer (listing B.3). The only import thing here is the
name we define in line five since we need this name as a reference in other configurations.
```

```
Listing B.3: Self-signed root
1 ---
2 ap iVe r s i on: c e rt−manage r. i o/v1
3 k i nd: Cl us t e r I s sue r
4 me t ada t a:
5 name: s e l f s i gned−r oot− i s suer− iv−s s as−examp l e
6 name spac e: c e rt−manage r
7 spe c:
8 s e l f S i gned: {}
When we have the self-signed issuer, we can continue; we first create a default cluster
issuer (CA) for our cluster, see listing B.4.
```

```
Listing B.4: Definition for a cluster issuer
1 ---
2 ap iVe r s i on: c e rt−manage r. i o/v1
3 k i nd: Cl us t e r I s sue r
4 me t ada t a:
5 name: de f au l t−examp l e−ca−i s sue r
6 name spac e: c e rt−manage r
7 spe c:
8 ca:
9 s e c r e tName: de f au l t−ca−s e c r e t
Again, we create a cluster issuer, creating a CA that can issue certificates in all
namespaces. Important is again then name in line five; we reference this later. Also
important is the secretName in line nine. When we request a certificate, the secret will
be needed/created, which we will do in listing B.5.
```

Listing B.5: A cluster issuer certificate
1 ---
2 ap iVe r s i on: c e rt−manage r. i o/v1
3 k i nd: Ce r t i f i ca t e
4 me t ada t a:
5 name: de f au l t−examp l e−ca
6 name spac e: c e rt−manage r
7 spe c:
8 i sCA: t rue
9 commonName: De f au l t CA IV SSAS Examp l e Cl us t e r
10 sub j e c t:
11 o r gan i za t i ons:
12 - TU Be r l i n
13 o r gan i za t i ona lUn i t s:
14 - AOT
15 s e c r e tName: de f au l t−ca−s e c r e t
16 pr i va t eKey:
17 a l go r i thm: ECDSA
18 s i z e: 256
19 i s sue rRe f:

```
B. cert-manager IV Software Security for Autonomous Systems
```

20 name: s e l f s i gned−r oot− i s suer− iv−s s as−examp l e
21 k i nd: Cl us t e r I s sue r
22 g r oup: c e rt−manage r. i o

```
Although this looks a lot like the content of an X.509 certificate, it is only the content
used by cert-manager to create a certificate request resource. As always, in line five, we
define a name that can reference the certificate. Line eight is critical; here, we mark
a certificate as belonging to a CA. In listing ?? , you also can see that this is marked
as a critical X.509 extension in the issued certificate. In lines 9 to 14, we define who
the certificate belongs to. Since this is the root CA, it also will be the issuer. Line
15 defines a name for the secret that will contain the private key, the TLS certificate
chain/certificate, and the root CA certificate. For the root CA, both certificate entries
will be the same. In lines 16 to 18, we define the key pair that shall be created. We will
use an elliptic curve algorithm to produce it, with a key length of 256 bits. Lines 19-22
define the issuer we want to use for creating the certificate; for the root CA, this shall
be the previously generated self-signed issuer.
To check if your certificate has successfully been created, you can use this command:
```

```
kubectl wait --for=condition=Ready certificate -name default-example-ca \
-n cert-manager --timeout=300s
```

```
To identify problems or look at information related to the certificate, you can ask
Kubernetes to provide you with details by using this command:
```

```
kubectl describe certificate -name default-example-ca \
-n cert-manager
```

```
Looking at the actual certificate will be a little bit more work. First, we have to get
the secret with the certificate from Kubernetes. We do this with:
```

```
kubectl get secrets default-ca-secret -n cert-manager -o yaml
```

```
This will result in the secret, as can be seen in listing B.6.
```

```
Listing B.6: Root CA secret
1 %
2 ap iVe r s i o n: v1
3 da t a:
4 c a. c r t : LS0 tLS1CRUdJTiBDRVJUSUZJQ0FURS0 tLS0 tCk1JSUI z ekNDQVlXZ0F3SUJBZ0 l SQ
Ul vbk1xZStKb3JZT1JYUUQr d3N1N1V3Q2dZSUtvWk l6 a jBFQXdJd1R6RVMKTUJBR0ExVU
VDaE1KVkZVZ1FtVn l iR2x1TVF3d0NnWURWUVFMRXdOQlQxUXhLekFwQmdOVkJBTVRJa1J
sWm1GMQp i SFFnUTBFZ1NWWWdVMU5CVXlCRmVHRnRjR3hsSUVOc2RYTjBaWEl3 SGh jTk1q
VXdNakUzTVRBd016STBXaGNOCk1qVXdOVEU0TVRBd016STBXakJQTVJJd0VBWURWUVFLR
XdsVVZTQkNaWEpzYVc0 eEREQUtCZ05WQkFzVEEwRlAKVkRFck1Da0dBMVVFQXhNaVJHV
m1ZWFZzZENCRFFTQkpWaUJUVTBGVElFVjRZVzF3YkdVZ1EyeDFjM1JsY2pCWgpNQk1HQn
l xR1NNNDlBZ0VHQ0NxR1NNNDlBd0VIQTBJQUJ I cmpISGtKSHFRSks1QXd iUk9VQVNFQkF
```

```
B. cert-manager IV Software Security for Autonomous Systems
```

NMVdhYTMyCj l aUnR3TmZDNHE5RGp1VTNtRjNRa3hUa2R3Qkh iOTBNTmJMM3NFUi9QYmRx
SG9OQ2Fl cVB5aUtqUWpCQU1BNEcKQTFVZER3RUI vd1FFQXdJQ3BEQVBCZ05WSFJNQkFmO
EVCVEFEQVFI L01CMEdBMVVkRGdRV0JCU3VuUUpvQ0lYdApRUmRqZUVZMUJ l dkE3SENUY2
pBS0JnZ3Foa2pPUFFRREFnTk lBREJGQWlBSXNHMUU5RXpGaC9ZWHptdmdBM0lWCmx4SCs
xcng1Rmx1bTBVY3dBbVpqTndJaEFKeHpTOEQvOER2azA1YnRURFJ6MU1iYjNmMzk2S0g r
RHJydkNITm8KYkhUMgo tLS0 tLUVORCBDRVJUSUZJQ0FURS0 tLS0 tCg==
5 t l s. c r t : LS0 tLS1CRUdJTiBDRVJUSUZJQ0FURS0 tLS0 tCk1JSUI z ekNDQVlXZ0F3SUJBZ0 l S
QUl vbk1xZStKb3JZT1JYUUQr d3N1N1V3Q2dZSUtvWk l6 a jBFQXdJd1R6RVMKTUJBR0ExV
UVDaE1KVkZVZ1FtVn l iR2x1TVF3d0NnWURWUVFMRXdOQlQxUXhLekFwQmdOVkJBTVRJa1
JsWm1GMQp i SFFnUTBFZ1NWWWdVMU5CVXlCRmVHRnRjR3hsSUVOc2RYTjBaWEl3 SGh jTk1
qVXdNakUzTVRBd016STBXaGNOCk1qVXdOVEU0TVRBd016STBXakJQTVJJd0VBWURWUVFL
RXdsVVZTQkNaWEpzYVc0 eEREQUtCZ05WQkFzVEEwRlAKVkRFck1Da0dBMVVFQXhNaVJHV
m1ZWFZzZENCRFFTQkpWaUJUVTBGVElFVjRZVzF3YkdVZ1EyeDFjM1JsY2pCWgpNQk1HQn
l xR1NNNDlBZ0VHQ0NxR1NNNDlBd0VIQTBJQUJ I cmpISGtKSHFRSks1QXd iUk9VQVNFQkF
NMVdhYTMyCj l aUnR3TmZDNHE5RGp1VTNtRjNRa3hUa2R3Qkh iOTBNTmJMM3NFUi9QYmRx
SG9OQ2Fl cVB5aUtqUWpCQU1BNEcKQTFVZER3RUI vd1FFQXdJQ3BEQVBCZ05WSFJNQkFmO
EVCVEFEQVFI L01CMEdBMVVkRGdRV0JCU3VuUUpvQ0lYdApRUmRqZUVZMUJ l dkE3SENUY2
pBS0JnZ3Foa2pPUFFRREFnTk lBREJGQWlBSXNHMUU5RXpGaC9ZWHptdmdBM0lWCmx4SCs
xcng1Rmx1bTBVY3dBbVpqTndJaEFKeHpTOEQvOER2azA1YnRURFJ6MU1iYjNmMzk2S0g r
RHJydkNITm8KYkhUMgo tLS0 tLUVORCBDRVJUSUZJQ0FURS0 tLS0 tCg==
6 t l s. ke y: LS0 tLS1CRUdJTiBFQyBQUk lWQVRFIEtFWS0 tLS0 tCk1 IY0NBUUVFSUl l SEg1azh2
Z24zdHRMU0h jNEJXa jVrUEswYkFoMmpISkQ3UFJRd jNkc1RvQW9HQ0NxR1NNNDkKQXdFS
G9VUURRZ0FFZXVNY2VRa2VwQWtya0RCdEU1UUJJUUVBe l ZacHJmYjFsRzNBMThMaXIw
T081VGVZWGRDVApGT1I zQUVkd jNRdzFzdmV3Ukg4OXQyb2VnMEpwNm8vS0 l nPT0KLS0 tL
S1FTkQgRUMgUFJJVkFURSBLRVktLS0 tLQo=
7 k i nd: Se c r e t
8 me t ada t a:
9 anno t a t i on s :
10 c e rt−manage r. i o/a l t−name s : " "
11 c e rt−manage r. i o/c e r t i f i ca te−name : de f au l t−examp l e−ca
12 c e rt−manage r. i o/common−name : De f au l t CA IV SSAS Examp l e Cl us t e r
13 c e rt−manage r. i o/ ip−s an s : " "
14 c e rt−manage r. i o/ i s suer−g r oup: c e rt−manage r. i o
15 c e rt−manage r. i o/ i s suer−k i nd: Cl us t e r I s sue r
16 c e rt−manage r. i o/ i s suer−name : s e l f s i gned−r oot− i s suer− iv−s s as−examp l e
17 c e rt−manage r. i o/ sub j e ct−o r gan i za t i ona l un i t s : AOT
18 c e rt−manage r. i o/ sub j e ct−o r gan i za t i on s : TU Be r l i n
19 c e rt−manage r. i o/uri−s an s : " "
20 c r ea t i onTime s t amp: "2025− 02 −17T10:03:24Z"
21 l abe l s :
22 cont r o l l e r. c e rt−manage r. i o/ f a o: " t rue "
23 name : de f au l t−ca−s e c r e t
24 name spac e : c e rt−manage r
25 r e s our c eVe r s i o n: " 696 "
26 u i d: 798022 fd−e669− 4112 −937a−f9 dac e4 cab4b
27 typ e : kube rne t e s. i o/ t l s

```
The values of secrets are Base64 encoded, which means we first need to decode with:
```

```
base64 -d -i -
```

```
Suppose you copied the encoded string for tls.crt, you will get PEM encoded cer-
tificates, as can be seen in listing B.7.
```

```
B. cert-manager IV Software Security for Autonomous Systems
```

Listing B.7: PEM encoded X.509 certificate
1 −−−−−BEGIN CERTIFICATE−−−−−
2 MI IB3zCCAYWgAwIBAg IRAI onMqe+Jo rYORXQD+ws u7UwCgYIKoZI z j0EAwIwTzES
3 MBAGA1UEChMJVFUgQmVybGl uMQwwCgYDVQQLEwNBT1QxKzApBgNVBAMTI kRl ZmF1
4 bHQgQ0EgSVYgU1NBUyBFeGFt cGx l IENsdXN0ZXIwHhcNMjUwMj E3MTAwMz I0WhcN
5 MjUwNTE4MTAwMz I0WjBPMRIwEAYDVQQKEwlUVSBCZXJs aW4xDDAKBgNVBAsTA0FP
6 VDErMCkGA1UEAxMiRGVmYXVsdCBDQSBJViBTU0FTIEV4YW1wbGUgQ2x1c3Rl c jBZ
7 MBMGByqGSM49AgEGCCqGSM49AwEHA0IABHr jHHkJHqQJK5AwbROUASEBAM1Waa32
8 9ZRtwNfC4q9Dj uU3mF3QkxTkdwBHb90MNbL3sER/PbdqHoNCaeqPy iKjQjBAMA4G
9 A1UdDwEB/wQEAwICpDAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQWBBSunQJoCIXt
10 QRd j eEY1BevA7HCTc jAKBggqhk jOPQQDAgNIADBFAiAI sG1E9EzFh/YXzmvgA3IV
11 l xH+1r x5Fl um0UcwAmZjNwIhAJxzS8D/8Dvk05btTDRz1Mbb3 f396KH+Dr rvCHNo
12 bHT2
13 −−−−−END CERTIFICATE−−−−−

```
There is not much to see yet to print the actual certificate, we use openssl:
```

```
openssl x509 -text
```

Finally, we get a textual description for the X.509 certificate. The result is shown in
listing B.8.
Listing B.8: X.509 cluster issuer certificate (shown with openssl x509 -text)
1 Ce r t i f i ca t e :
2 Da t a:
3 Ve r s i o n: 3 (0 x2)
4 Se r i a l Numbe r :
5 8 a : 2 7 : 3 2 : a7: b e : 2 6 : 8 a: d 8 : 3 9 : 1 5 : d0:0 f : e c:2 c : bb: b5
6 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256
7 I s sue r : O=TU Be r l i n , OU=AOT, CN=De f au l t CA IV SSAS Examp l e Cl us t e r
8 Va l i d i ty
9 No t Be f o r e : Feb 17 10:03:24 2025 GMT
10 No t Af t e r : May 18 10:03:24 2025 GMT
11 Sub j e c t : O=TU Be r l i n , OU=AOT, CN=De f au l t CA IV SSAS Examp l e Cl us t e r
12 Sub j e c t Pub l i c Key In f o:
13 Pub l i c Key Al go r i thm: id−e cPub l i cKey
14 Pub l i c−Ke y: (256 b i t)
15 pub:
16 04:7 a: e3:1 c : 7 9 : 0 9 : 1 e : a4:09:2 b : 9 0 : 3 0 : 6 d : 1 3 : 9 4 :
17 0 1 : 2 1 : 0 1 : 0 0 : c d : 5 6 : 6 9 : a d: f 6 : f 5 : 9 4 : 6 d: c0: d7: c2:
18 e2: a f : 4 3 : 8 e : e 5 : 3 7 : 9 8 : 5 d: d 0 : 9 3 : 1 4 : e 4 : 7 7 : 0 0 : 4 7 :
19 6 f : dd:0 c : 3 5 : b2: f 7 : b0:44:7 f : 3 d: b7:6 a:1 e : 8 3 : 4 2 :
20 69: e a:8 f : c a:22
21 ASN1 OI D: pr ime256v1
22 NIST CURVE: P− 256
23 X509v3 ext ens i on s :
24 X509v3 Key Us ag e : c r i t i ca l
25 Di g i t a l S i gna tur e , Key Enc i phe rmen t , Ce r t i f i ca t e S i gn
26 X509v3 Ba s i c Cons t r a i nt s : c r i t i ca l
27 CA:TRUE
28 X509v3 Sub j e c t Key Ident i f i e r :
29 AE:9 D:02:68:08:85:ED : 4 1 : 1 7 : 6 3 : 7 8 : 4 6 : 3 5 : 0 5 :EB:C0:EC:70:93:72
30 S i gna tur e Al go r i thm: e cdsa−wi th−SHA256

```
B. cert-manager IV Software Security for Autonomous Systems
```

31 S i gna tur e Va l u e :
32 3 0 : 4 5 : 0 2 : 2 0 : 0 8 : b0:6 d:44: f 4 : 4 c : c 5 : 8 7 : f 6 : 1 7 : c e:6 b: e 0 : 0 3 :
33 7 2 : 1 5 : 9 7 : 1 1 : f e : d6:b c : 7 9 : 1 6 : 5 b: a6: d 1 : 4 7 : 3 0 : 0 2 : 6 6 : 6 3 : 3 7 :
34 0 2 : 2 1 : 0 0 : 9 c : 7 3 : 4 b: c0: f f : f 0 : 3 b: e4: d3:96: e d:4 c : 3 4 : 7 3 : d4:
35 c6: db:dd: f d: f d: e8: a1: f e:0 e : b a: e f : 0 8 : 7 3 : 6 8 : 6 c : 7 4 : f6

```
Now that we have a root CA in our cluster, we want to add an intermediate CA. In
listing B.2, we did this in lines four and 7. The main difference is that our intermediate
CA for the example will be located in the namespace iv-ssas-example, and instead
of cluster issuer (kind: ClusterIssuer), we will use only an issuer (kind: Issuer).
This means our intermediate CA can only issue certificates in one namespace. The issuer
definition also does not reference the self-signed issuer but the issuer we defined in listing
B.4.
If you look at the secret for the intermediate CA, you will also notice this time, the
values for ca.crt and tls.crt are different. ca.crt contains the certificate for our root
CA and tls.crt the one for our intermediate CA. Later, we will see that tls.crt can
contain a certificate chain. But we do not have a chain yet since we only defined a root
and one leaf below.
The next step, shown in listing B.9, is to add a default certificate for our ingress
controller.
```

```
Listing B.9: Create a default ingress certificate
1 kube c t l c r ea t e−f ${PKIDIR}/ng i nx−i ng r e ss−c e r t. yaml−n i ng r e ss−ng i nx
We are almost done with initializing our public key infrastructure in Kubernetes. The
next step is only for convenience. We will use Kyverno^3 to enforce a policy for creating
certificates^4
In listing B.10, we install Kyverno (line 1-6) in the cluster and apply our policy (line
8).
```

```
Listing B.10: Install Kyverno and apply policy
1 he lm upg r ade−−i ns t a l l kyve rno kyve rno/kyve rno \
2 −−name spac e kyve rno−sys t em \
3 −−ve r s i on $KYVERNO_CHART_VERSION \
4 −−c r ea te−name spac e
5
6 kube c t l wa i t−−f or=cond i t i on=Ready pod−l app. kube rne t e s. i o/ i ns t ance=kyve rn
o−n kyve rno−sys t em−−t imeout=300 s
7
8 kube c t l app l y−f ${PKIDIR}/po l i c i e s/ cpol−mut a te−c e r t i f i ca t e s−1.yaml
As a last step, we install trust-manager. trust-manager is used to tell pods which CA
they can trust; this is needed since pods typically may only have a default list of publicly
known and trusted CAs and do not know anything about our CAs. With trust-manager,
```

(^3) https://kyverno.io/
(^4) We use this https://cert-manager.io/v1.14-docs/tutorials/certificate-defaults/, but
Kyverno also provides additional policies for cert-manager https://kyverno.io/policies/
?policytypes=Cert-Manager.

```
B. cert-manager IV Software Security for Autonomous Systems
```

```
we can add our CAs to the list of trusted CAs or replace that list in pods with our CAs.
The installation of trust-manager is shown in listing B.11.
```

```
Listing B.11: Install trust-manager
```

1 he lm upg r ade−−i ns t a l l t rust−manage r j e t s t ack/ t rust−manage r \
2 −−name spac e c e rt−manage r \
3 −−s e t app. t rus t. name space=" iv−s s as−examp l e " \
4 −−ve r s i on $TRUST_MANAGER_CHART_VERSION−−wa i t
5
6 kube c t l wa i t−−f or=cond i t i on=Ready pod−l app=t rust−manage r−n c e rt−manage r
−−t imeout=300 s
Suppose we want to use the CA root certificates distributed by trust-manager in a
Quarkus microservice. In that case, we need to add a few configuration properties, as
seen in listing B.12.

```
Listing B.12: Configure Quarkus to trust our root CA(s).
```

1 qua rku s. kube rne t e s. con f ig−map−vo l ume s. " t rus t ed−cas−vo l ume ". con f ig−map−name=
iv−s s as−ca
2 qua rku s. kube rne t e s .mount s. " t rus t ed−cas−vo l ume ". pa th=/e t c/ c e r t s
3 qua rku s. kube rne t e s .mount s. " t rus t ed−cas−vo l ume ". r ead−on ly=t rue
4
5 qua rku s. r e st−c l i en t. t rust−s t o re=/e t c/ c e r t s/bund l e. p12
6 qua rku s. r e st−c l i en t. t rust−s t o re−pa s swo rd=" "

```
In lines 1-3, we mount a Kubernetes ConfigMap as a volume, similar to the approach
for mounting a secret (see listing 2.3). In case you are wondering where the name for
the ConfogMap in line 1 is coming from, it is the name we have given to the certificate
request in the Kubernetes YAML file for the intermediate CA^5.
Quarkus expects a keystore with trusted CAs, which we will provide from the mounted
volume. The file name is a key in the ConfigMap, and the value for that key is the file’s
contents. The file name is supplied in line 5, and the password is in line 6, which
trust-manager left empty.
```

(^5) See file bin/pki/ivssasexample-ca-cert.yaml in the SSAS cluster project.

## Acronyms

**AES** Advanced Encryption Standard.

**AI** artificial intelligence.

**BCP** Best Current Practices.

**CA** certification authority.

**CAPEC** Common Attack Pattern Enumerations and Classifications.

**CIA** confidentiality, integrity, availability.

**CRL** certification revocation list.

**CVE** Common Weakness Enumeration.

**CWE** Common Weakness Enumeration.

**DDoS** distributed denial of service.

**MAS** multiagent systems.

**mTLS** mutual TLS.

**OWASP** Open Worldwide Application Security Project.

**PKI** Public Key Infrastructure.

**PRNG** pseudo-random number generator.

**SDLC** software development life-cycle.

**TLS** transport layer security.

**TTP** tactics, techniques, and procedures.

**UML** unified modeling languange.

**XSS** cross-site scripting.

##### 202

## Glossary

**Adversary** The entity (person, group, or institution) that may try to compromise an
asset.

**Asset** Assets are what we need to consider and protect since they are valuable to us.
What we consider an Asset depends on the application, companies, and users. The
following definition provides a few examples: “An asset is anything of value, which
in the security context could refer to information, hardware, intellectual property,
prestige, and reputation.” [4].

**Attack Pattern** “ ‘Attack Patterns’ describes the common elements and techniques used
in attacks against vulnerable cyber-enabled capabilities. Attack patterns define
the challenges an adversary may face and how they solve them. They derive
from design patterns applied in a destructive rather than constructive context and
are generated from in-depth analysis of specific real-world exploit examples. ”
[http://capec.mitre.org/about/index.html.](http://capec.mitre.org/about/index.html.)

**Availability** The third and final CIA goal addresses issues surrounding guaranteeing
access to resources and assets. This goes beyond data and includes (business)
processes and system functionality. Here, we also should consider how a secure
state can be ensured, even in the presence of partial or total system failure.

**Cipher** Algorithm for transforming plain text to cipher text.

**Cipher text** In cryptology, the cipher text is the message a sender sends to the recipient.
It is the encrypted/encoded/enciphered version of the plain text.

**Computational Security** Given limited state-of-the-art computing resources (e.g., the
time needed for calculations is greater than the age of the universe), the cipher
cannot be broken.

**Confidentiality** A central goal is to keep information secret from other parties; only
authorized entities can see this information. To achieve the concealment of data,
one could use cryptography. Other parties may still observe that some information
is present but cannot see what it is since this is kept secret. Sometimes secrecy
alone is not enough; here, it may be required to hide the existence of information
completely. Steganography, for example, could be used to achieve this.

**Cryptanalysis (codebreaking)** The study of principles/ methods of deciphering cipher
text without knowing a key.

##### 203

Glossary IV Software Security for Autonomous Systems

**Cryptography** Study of encryption principles/methods.

**Cryptology** The field of both cryptography and cryptanalysis.

**Decipher (decrypt)** Recovering plain text from cipher text.

**Encipher (encrypt)** Converting plain text to cipher text.

**Exploit** “The method by which a threat can harm an asset is an exploit.”[4].

**Integrity** Information or data may be confidential or visible to everyone. In both cases, it
may be required that it is possible to detect modification of data. With methods for
integrity, we address this issue; one possible approach is the use of hash functions.

**Key** Information used in a cipher, known only to sender and/or receiver.

**Plain text** In cryptology, the plain text is the message a sender wants to send to the
recipient.

**Risk** “Risk is a measure of danger to an asset.” [4] “risk = threat x vulnerability x asset
value” [4]..

**Software development life-cycle** Describes the phases in the developing software, this
includes, for example, the phases requirements gathering, design, development &
testing, deployment, and operation.

**Threat** A threat is something we must worry about and try to prevent from becoming
a reality. Various definitions exist in the literature for this term: “A threat is a
potential violation of security.” [5], “A threat is a potential occurrence that can
have an undesirable effect on the system assets or resources. It is a danger that can
lead to undesirable consequences.” [5], “A threat is a party with the capabilities
and intentions to exploit a vulnerability in an asset.”, and “1: an expression of
intention to inflict evil, injury, or damage 2: one that threatens 3: an indication
of something impending _<_ the sky held a ̃ of rain _>_ ” [28]. Personally I prefer the
definitions by Bishop [5] and therefore will use the term _threat_ according to his
definition.

**Threat Modeling** Threat modeling is a formal process to identify and describe threats.
We will not cover this in detail in the lecture. Especially not the various existing
approaches. If you like to learn more, you could check out [38].

Glossary IV Software Security for Autonomous Systems

**Unconditional Security** No matter how much computer power is available, the cipher
cannot be broken since the cipher text provides insufficient information to deter-
mine the corresponding plain text.

**Vulnerability** Vulnerabilities are what we try to avoid since we can introduce them to
a software product through the errors or dependencies we use and link to our
product. A bit formal definition is the following: “A vulnerability is a weakness
that makes it possible for a threat to occur.”[5].

**Weakness** “a type of mistake in software that, in proper conditions, could contribute
to introducing vulnerabilities within that software. This term applies to mistakes
regardless of whether they occur in implementation, design, or other phases of the
SDLC”[29].

**Weakness vs. Vulnerability** “Software weaknesses are errors that can lead to software
vulnerabilities. A software vulnerability,... , is a mistake in software that can
be directly used by a hacker to gain access to a system or network.” https:
//cwe.mitre.org/about/faq.html **Weakness** : Something that can exist in a
software product. Entries in CWE are not related to a specific software product.
**Vulnerability** : Something that exists in a specific software product. Vulnerabili-
ties are not contained in the CWE but in the CVE.

## Index

abstraction, 66
access control, 6
adversary, 5, 7, 203
AES, _see_ cipher
asset, 5, 7, 178, 203
asymmetric cipher, 19
asymmetric encryption, _see_ cipher
ATT&CK, _see_ MITRE ATT&CK
attack pattern, 52, 53, 60, 203
attack tree, 59–61
attacker, _see_ aversary8, _see_ adversary
authentication, 10
authorization, 10
autonomous system, 3
autonomous systems, 3
availability, 7, 203

C4, 62, 180
CAPEC, 8, 53
cert-manager, 24, 195–200
certificate, 10, 20, _see_ X.509, certificate
certificate authority, 20, 196
choke point, 6
CIA, _see_ confidentiality, integrity, avail-
ability
cipher, 13, 14, 203
AES, 15
asymmetric, 10, 13, 16–18
cipher text, 13, 203
key, 13, 204
private, 16
public, 10, 16, 20
size, 18
plain text, 13, 17, 204
symmetric, 10, 13–16, 18
cipher text, 14, 15, 17
cohesion, 66

```
Common Attack Pattern Enumerations
and Classifications, see CAPEC
Common Vulnerabilities and Exposures,
see CVE
Common Weakness Enumeration, see
CWE
completeness, 66
computational security, 14, 17, 203
confidentiality, 7, 10, 12–18, 24, 203
coupling, 66
cryptanalysis, 13, 203
cryptography, 7, 13, 204
cryptology, 13, 204
CVE, 8, 52, 205
CWE, 8, 51, 205
cyber kill chain, 52, 53
```

```
decipher, 13, 204
decomposition, 66
Design, 6, 62–70
design pattern, 67–70
API Gateway, see microservices,
pattern, API Gateway
MVC, 67
Development, 6
digital signature, 10, 18–20
digitial signature, 16
distributed system, 3
```

```
encapsulation, 66
encipher, 13, 204
exploit, 204
exposure, 52
```

```
hash function, 7, 10, 11, 204
SHA-1, 12
```

```
identification, 24
```

##### 206

Index IV Software Security for Autonomous Systems

information hiding, 66
integrity, 7, 10–12, 24, 204

Kerckhoffs’s principle, 13
key, _see_ cipher
kill chain, _see_ cyber kill chain
Kind, 195
Kubernetes, 24
Kyverno, 24, 200

microservices, 3–5
pattern, 68
API Gateway, 69
misuse case, 55–60
MITRE ATT&CK, 8, 53
modularization, 66
multiagent system, 3

non-repudiation, 18

Operation, 6
OWASP, 53, 54

penetration testing, 6
PGP, 20
PKI, 10, 20–22, 195
X.509, _see_ X.509
plain text, 15
plain text , 14
primitiveness, 66
privilege, 6
pseudo-random number, 10
public key, _see_ cipher

quantum computing, 14, 17
Quarkus, 24

random number, 23
pseudo-random number, 23
seed value, 23
Requirements, 5
requirements, 46–61
security, 48–61
software, 46
risk, 5, 7, 204

```
SDLC, see software development life-
cycle
security pattern
API Gateway, see microservices,
pattern, API Gateway
security requirements, see requirements,
security
attack tree, see attack tree
misuse case, see misuse case
self-adaptive syste,s, 3
self-healing, 3
self-protection, 3
separation of
concern, 66
interface and implementation, 66
software development life-cycle, 5–6
software requirements, see requirements,
software
steganography, 7
STRIDE, 55
sufficiency, 66
symmetric encryption, see cipher
```

```
testing, 6
penetration, 53
security, 53
threat, 5, 7, 204, 205
modeling, 7, 55, 204
threat actor, see aversary8, see adver-
sary
TLS, 10, 24–32
mTLS, 24
token, 10
trust-manager, 24, 200
```

```
UML, 55, 62, 65, 180
use case, see use case diagram
unconditional security, 14, 205
use case diagram, 55
```

```
vulnerability, 6, 7, 51, 60, 205
vs. weakness, 7, 52, 205
```

```
weakness, 7, 51, 60, 205
vs. vulnerability, 7, 52, 205
```

Index IV Software Security for Autonomous Systems

web of trust, 20

X.509, 10, 20, 21
certificate, 24–32, 45

```
certificate chain, 20
certificate validation, 21
CRL, 21
```

## Bibliography

```
[1] Ian Alexander. Misuse cases: use cases with hostile intent. IEEE Software , 20(1):58–
66, 2003.
```

```
[2] Julia H. Allen. “Plan, Do, Check, Act", 2013. https://buildsecurityin.us-
cert.gov/articles/best-practices/deployment-and-operations/plan-do-check-act
(2019-01-08).
```

```
[3] B Arkin, S Stender, and Gary McGraw. Software penetration testing. IEEE Security
& Privacy , 3(1):84–87, 2005.
```

```
[4] Richard Bejtlich. The Tao of Network Security Monitoring. Addison-Wesley, 2005.
```

```
[5] Matt Bishop. Computer Security – Art and Science. Addison-Wesley, 2003.
```

```
[6] Pierre Bourque and Richard E. Fairley, editors. SWEBOK v3.0 - Guide to the
Software Engineering Body of Knowledge. IEEE, 2014. https://www.computer.
org/education/bodies-of-knowledge/software-engineering.
```

```
[7] John Clingan and Ken Finnigan. Kubernetes Native Microservices with Quarkus
and MicroProfile. Manning, 2022.
```

```
[8] David Cooper, Stefan Santesson, Stephen Farrell, Russ Housley, Sharon Boeyen,
and Tim Polk. Internet X.509 Public Key Infrastructure Certificate and Certifi-
cate Revocation List (CRL) Profile. Request for Comments RFC 5280, Internet
Engineering Task Force, May 2008.
```

```
[9] Gadi Evron. Battling Botnets and Online Mobs. Georgetown Journal of Interna-
tional Affairs , 9:121–126, 2008.
```

[10] Eduardo Fernandez-Buglioni. _Security Patterns in Practice: Designing Secure Ar-
chitectures Using Software Patterns_. Wiley Publishing, 2013.

[11] David Ferraiolo, Ravi Sandhu, Serban Gavrila, D Kuhn, and Ramaswamy Chan-
dramouli. Proposed NIST standard for role-based access control. _ACM Transactions
on Information and System Security_ , 4(3):224–274, 2001.

[12] Donald Firesmith. Security Use Cases. _The Journal of Object Technology_ , 2(3):53–
64, 2003.

[13] Donald Firesmith. Specifying Reusable Security Requirements. _The Journal of
Object Technology_ , 3(1):61–75, 2004.

##### 209

Bibliography IV Software Security for Autonomous Systems

[14] Donald G Firesmith. Engineering Security Requirements. _Journal of Object Tech-
nology_ , 2(1):53–68, 2003.

[15] Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides. _Design Pat-
terns: Elements of Reusable Object-oriented Software_. Addison-Wesley Longman
Publishing Co., Inc., Boston, MA, USA, 1995.

[16] Mark G. Graff and Kenneth R. van Wyk. _Secure Coding - Principles & Practices_.
O’Reilly, 2003.

[17] Paco Hope, Gary McGraw, and Annie I. Anton. Misuse and abuse cases: getting
past the positive. _IEEE Security & Privacy_ , 2(3):90–92, 2004.

[18] Markus C. Huebscher and Julie A. McCann. A survey of autonomic comput-
ing—degrees, models, and applications. _ACM Computing Surveys_ , 40(3):7:1–7:28, 2008.

[19] SWEBOK Wiki. [http://swebokwiki.org/Main_Page.](http://swebokwiki.org/Main_Page.) (2018-07-26).

[20] Dan Bergh Johnsson, Daniel Deogun, and Daniel Sawano. _Secure ny Design_. Man-
ning, 2019.

[21] David Kahn. _The Codebreakers_. Scribner, 2 edition, 1996.

[22] Jeffrey O Kephart and David M. Chess. The vision of autonomic computing. _Com-
puter_ , 36(1):41–50, 2003.

[23] Loren M. Kohnfelder. _Towards a Practical Public-key Cryptosystem_. Bacehlor thesis,
Massachusetts Institute of Technology, 1978.

[24] Michael Lesk. The New Front Line: Estonia under Cyberassault. _IEEE Security &
Privacy_ , 5:76–79, 2007.

[25] Marko Luksa. _Kubernetes in Action_. Manning Punlications, 2018.

[26] Neil Madden. _API Security in Action_. Manning, 2020.

[27] Gary McGraw. Automated Code Review Tools for Security. _Computer_ , 41(12):108–
111, 2008.

[28] Merriam-Webster’s Collegiate Dictionary, 2003.

[29] Common Weakness Enumeration (CWE). [http://cwe.mitre.org/documents/glossary,](http://cwe.mitre.org/documents/glossary,)
(Last accessed 2018-08-07).

[30] Sam Newman. _Building Microservices_. O’Reilly Media, Inc., 2. edition, 2021.

[31] Liz Rice. _Container Security_. O’Reilly Media, Inc., 2020.

[32] Chris Richardson. _Microservices Patterns_. Manning, 2019.

Bibliography IV Software Security for Autonomous Systems

[33] Jerome H. Saltzer and Michael D. Schroeder. The protection of information in
computer systems. _Proceedings of the IEEE_ , 63(9):1278–1308, 1975.

[34] Hartmut Schmeck, Christian Müller-Schloer, Emre Çakar, Moez Mnif, and Urban
Richter. Adaptivity and self-organization in organic computing systems. _ACM
Transactions on Autonomous and Adaptive Systems_ , 5(3):10:1–10:32, 2010.

[35] Bruce Schneier. _Applied Cryptography_. John Wiley & Sons, Inc., 2 edition, 1996.

[36] Bruce Schneier. Attack Trees. _Dr. Dobb’s Journal,_ , 24(12):21–29, 1999.

[37] Markus Schumacher, Eduardo Fernandez, Duane Hybertson, and Frank
Buschmann. _Security Patterns: Integrating Security and Systems Engineering_. John
Wiley & Sons, Inc., USA, 2005.

[38] Adam Shostack. _Threat Modeling - Designing for Security_. Wiley Publishing, 2014.

[39] Guttorm Sindre and Andreas L Opdahl. Eliciting security requirements by misuse
cases. In _37th International Conference on Technology of Object-Oriented Languages
and Systems_ , pages 120–131, 2000.

[40] Simon Singh. _The Code Book - The Secret History of Codes and Code-breaking_.
Fourth Estate, 2000.

[41] Prabath Siriwardena and Nuwan Dias. _Microservices Security in Action_. Manning, 2020.

[42] Ian Sommerville. _Software Engineering_. Pearson, 10. edition, 2016.

[43] Ian Sommerville and Pete Sawyer. _Requirements Engineering: A Good Practice
Guide_. John Wiley & Sons Ltd., 1997.

[44] William Stallings. _Cryptography and Network Security_. Prentice Hall International,
5 edition, 2010.

[45] Chris Steel, Ramesh Nagappan, and Ray Lai. _Core Security Patterns: Best Prac-
tices and Strategies for J2EET M, Web Services, and Identity Management_. Prentice
Hall, 2005.

[46] A. Takanen, J. D. DeMott, and C. Miller. _Fuzzing for Software Security Testing
and Quality Assurance_. Artech House, 2008.

[47] Inger Anne Tøndel, Jostein Jensen, and Lillian Røstad. Combining Misuse Cases
with Attack Trees and Security Activity Models. In _Availability, Reliability, and
Security_ , pages 438–445, 2010.

[48] Katrina Tsipenyuk, Brian Chess, and Gary McGraw. Seven pernicious kingdoms: a
taxonomy of software security errors. _IEEE Security & Privacy_ , 3(6):81–84, 2005.

Bibliography IV Software Security for Autonomous Systems

[49] Ken van Wyk. Adapting penetration testing for software development
purposes. https://buildsecurityin.us-cert.gov/articles/best-practices/security-
testing/adapting-penetration-testing-software-development-purposes, 2007.

[50] K.R van Wyk and Gary McGraw. Bridging the gap between software development
and information security. _IEEE Security & Privacy_ , 3(5):75–79, 2005.

[51] John Viega and Gary McGraw. _Building Secure Software_. Addison-Wesley, 2002.

[52] Danny Weyns, Sam Malek, and Jesper Andersson. FORMS: Unifying reference
model for formal specification of distributed self-adaptive systems. _ACM Transac-
tions on Autonomous and Adaptive Systems_ , 7(1):8:1–8:61, 2012.

[53] Karl Wiegers and Joy Beatty. _Software Requirements_. Microsoft, 3rd edition, 2013.

[54] Eric Yuan, Naeem Esfahani, and Sam Malek. A Systematic Survey of Self-Protecting
Software Systems. _ACM Transactions on Autonomous and Adaptive Systems_ ,
8(4):17:1–17:41, 2014.

## Todo list

##### 213
