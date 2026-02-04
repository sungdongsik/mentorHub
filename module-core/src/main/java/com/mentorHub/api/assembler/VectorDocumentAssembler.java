package com.mentorHub.api.assembler;

import org.springframework.ai.document.Document;

public interface VectorDocumentAssembler<T> {
    Document assemble(T data);
}
