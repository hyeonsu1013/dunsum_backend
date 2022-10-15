package com.dunsum.backend.outside.dnf.model;

import com.dunsum.backend.outside.model.OtsdModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DnfSrchModel extends OtsdModel {

    String a;
}
