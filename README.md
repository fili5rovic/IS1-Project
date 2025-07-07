# Audio Streaming System

This project implements a distributed audio streaming system using Java, REST, and JMS. It simulates a full-featured platform where users can upload, listen to, and rate audio recordings, manage subscriptions, and interact with content categorized by genres and metadata.

## Description

The system consists of the following components:

### 1. Client Application (Java SE)
- Sends REST requests to the central server.
- Can be CLI or GUI-based.
- Displays server responses to the user.

### 2. Central Server
- Acts as a middleware between the client and the subsystems.
- Receives REST requests, forwards them via JMS to the corresponding subsystem.
- Does not store data.

### 3. Subsystems
- **Subsystem 1**: Manages users and cities.
- **Subsystem 2**: Manages audio recordings and categories.
- **Subsystem 3**: Manages packages, subscriptions, ratings, listens, and favorite lists.

All subsystems use MySQL as a database and communicate only via JMS.

## Data Model Overview

- **User**: name, email, birth year, gender, city
- **City**: name
- **Audio**: name, duration, owner, timestamp, multiple categories
- **Category**: name
- **Package**: name, current monthly price
- **Subscription**: user, package, start time, price at the time, unique per user/month
- **Listening**: user, audio, timestamp, start second, duration
- **Rating**: user, audio, score (1–5), timestamp
- **Favorites**: user, audio (unique per user)

## Requirements

1. MySQL Server + Workbench or equivalent.
2. Java (client, server, and subsystems).
3. JMS implementation

## Supported Operations (via REST)

### User and City (Subsystem 1)
- Create city
- Create user
- Change user email / city
- List all cities / users

### Audio and Category (Subsystem 2)
- Create category / audio
- Edit audio name / add category
- Delete audio (by owner)
- List all categories / audio / audio's categories

### Subscription and Playback (Subsystem 3)
- Create package / change price
- Subscribe user to a package
- Listen to audio
- Add to favorites
- Rate (create/update/delete)
- View subscriptions / listens / ratings / favorites
