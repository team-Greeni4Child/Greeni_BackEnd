package com.greeni.api.activities.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeni.api.activities.docs.ActivityControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActivityController implements ActivityControllerDocs {
}
