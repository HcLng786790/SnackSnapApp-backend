package com.huuduc.snacksnap.data.dto;

import com.huuduc.snacksnap.enu.EMailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mail DTO Request
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTPRequest {

    private String to;
    private String subject;
//    private String namePost;
//    private String nameReceive;
//    private EMailType typeMail;
    private String body;

}
