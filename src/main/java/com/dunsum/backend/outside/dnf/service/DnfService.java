package com.dunsum.backend.outside.dnf.service;

import com.dunsum.backend.outside.dnf.model.DnfSrvrModel;

import java.util.List;

public interface DnfService {

    List<DnfSrvrModel> selServers() throws Exception;

}
