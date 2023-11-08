package com.engineeringthesis.visitservice.model;

public enum VisitStatus {
    SCHEDULED, //The visit has been scheduled but has not yet started.
    COMPLETED, // The visit has been completed, and all procedures have been carried out.
    CANCELLED // The visit has been cancelled for various reasons.
}
