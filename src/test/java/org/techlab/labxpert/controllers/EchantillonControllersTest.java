package org.techlab.labxpert.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.techlab.labxpert.Enum.RoleUser;
import org.techlab.labxpert.Enum.StatutEchantillon;
import org.techlab.labxpert.dtos.EchantillonDTO;
import org.techlab.labxpert.dtos.NormeDTO;
import org.techlab.labxpert.dtos.PatientDTO;
import org.techlab.labxpert.dtos.UtilisateurDTO;
import org.techlab.labxpert.entity.Echantillon;
import org.techlab.labxpert.entity.Patient;
import org.techlab.labxpert.entity.Utilisateur;
import org.techlab.labxpert.service.*;
import org.techlab.labxpert.service.serviceImp.EchantillonServiceImp;
import org.techlab.labxpert.service.serviceImp.PatientServiceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EchantillonController.class)
@ExtendWith(SpringExtension.class)
public class EchantillonControllersTest {
    @MockBean
  EchantillonServiceImp echantillonServiceImp;
    @Autowired
    MockMvc mockMvc;
    @MockBean
     I_Analyse i_analyse;
    @MockBean
    I_Patient i_patient;
    @MockBean
    I_Utilisateur i_utilisateur;
    @MockBean
    I_Outil_Echantillon i_outil_echantillon;
    @MockBean
    I_Outil i_outil;

    @MockBean
    EchantillonDTO echantillonDTO;
    @MockBean
   PatientDTO patientDTO;
    @MockBean
   UtilisateurDTO utilisateurDTO;

    @BeforeEach

    void setUp() throws ParseException {
        ModelMapper modelMapper=new ModelMapper();
        Date date;
        SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd");

        patientDTO=new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setNom("mohammed");
        patientDTO.setPrenom("prenom mohammed");
        patientDTO.setAdresse("qwerty");
        patientDTO.setTel("02125232525");
        patientDTO.setSexe("Male");
        date = inputFormat.parse("2000-02-02");
        patientDTO.setDateNaissance(date);
       // PatientDTO patientDTO1=i_patient.addPatient(patientDTO);
        /***************************************************************/
        utilisateurDTO=new UtilisateurDTO();
        utilisateurDTO.setIdUtilisateur(1L);
        utilisateurDTO.setNom("imad");
        utilisateurDTO.setPrenom("prenom imad");
        utilisateurDTO.setAdresse("azerty");
        utilisateurDTO.setTel("147852369");
        utilisateurDTO.setSexe("Male");
        date = inputFormat.parse("2000-02-02");
        utilisateurDTO.setDateNaissance(date);
        utilisateurDTO.setPassword("123456");
        utilisateurDTO.setNomUtilisateur("qwerty");
        utilisateurDTO.setRole(RoleUser.Preleveur);
       // UtilisateurDTO utilisateurDTO1=i_utilisateur.addUser(utilisateurDTO);
        /*************************************************************/
        echantillonDTO=new EchantillonDTO();
        System.out.println(patientDTO);
        echantillonDTO.setPatient(modelMapper.map(patientDTO, Patient.class));

        echantillonDTO.setStatus(StatutEchantillon.EnAttente);
        echantillonDTO.setUtilisateur(modelMapper.map(utilisateurDTO, Utilisateur.class));
        date = inputFormat.parse("2024-01-18");
        echantillonDTO.setDatePrelevement(date);
        echantillonDTO.setTypeAnalyse("Biochimie");
    }

    @Test
    public void test_saveEchantillon() throws Exception {
        when(echantillonServiceImp.addEchantillon(echantillonDTO)).thenReturn(echantillonDTO);
        mockMvc.perform(post("/api/V2/Echantillon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(echantillonDTO)))
                .andExpect(status().isCreated());
    }
    @Test
    public  void test_getEchantillonList() throws Exception {
        List<EchantillonDTO> echantillonList =new ArrayList<>();

        when(echantillonServiceImp.showEhantillon()).thenReturn(echantillonList);
        mockMvc.perform(get("/api/V2/Echantillon")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void test_deleteContoller() throws Exception {
        // Mock the behavior of delNorme to do nothing when called with normeDTO
        doReturn(null).when(echantillonServiceImp).delEchantillhon(echantillonDTO);

        mockMvc.perform(delete("/api/V2/Echantillon/{id}", 1)  // Replace '1' with the actual ID
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(echantillonDTO)))
                .andExpect(status().isOk());
    }
    @Test
    public void test_modEchantillon() throws Exception {
        when(echantillonServiceImp.modEchantillon(echantillonDTO)).thenReturn(echantillonDTO);
        mockMvc.perform(put("/api/V2/Echantillon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(echantillonDTO)))
                .andExpect(status().isCreated());
    }



}
