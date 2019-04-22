package mf.com.aidl_service;

import mf.com.aidl_service.model.Person;

interface AIDL{
    void addPerson(in Person person);
    List<Person> getPersons();
}