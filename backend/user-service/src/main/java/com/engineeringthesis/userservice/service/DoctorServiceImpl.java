package com.engineeringthesis.userservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.userservice.entity.Doctor;
import com.engineeringthesis.userservice.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements CrudService<Doctor> {
    private final DoctorRepository doctorRepository;

    @Override
    public void save(Doctor doctor) {
        String PESEL = doctor.getPesel();
        try {
            if (doctorRepository.existsByPesel(PESEL)) {
                String message = String.format("Doctor with this PESEL: %s already exists in database!", PESEL);
//                throw new EntityIdAlreadyExistException(message);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            doctorRepository.save(doctor);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Doctor with this PESEL: %s", PESEL);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Doctor getById(Long id) {
        String message = String.format("Doctor with this doctorId: %d doesn't exist in database!", id);
//        return doctorRepository.findByDoctorId(id).orElseThrow(() -> new ObjectDoesNotExistException(message));
        return doctorRepository.findByDoctorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    public Doctor getByPesel(String pesel) {
        String message = String.format("Doctor with this PESEL: %s doesn't exist in database!", pesel);
//        return patientRepository.findByPatientId(id).orElseThrow(() -> new ObjectDoesNotExistException(message));
        return doctorRepository.findByPesel(pesel).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }


    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public void update(Doctor doctor) {
        Long doctorId = doctor.getDoctorId();
        try {
            Doctor oldDoctor = doctorRepository.findByDoctorId(doctorId).orElseThrow(() -> {
                String message = String.format("%s with this doctorId: %d doesn't exist in database!", doctor.getClass().getName(), doctorId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            doctor.setDoctorId(oldDoctor.getDoctorId());

            if (doctor.getFirstName() != null && !Objects.equals(doctor.getFirstName(), oldDoctor.getFirstName())) {
                oldDoctor.setFirstName(doctor.getFirstName());
            }

            if (doctor.getLastName() != null && !Objects.equals(doctor.getLastName(), oldDoctor.getLastName())) {
                oldDoctor.setLastName(doctor.getLastName());
            }

            if (doctor.getBirthDate() != null && !Objects.equals(doctor.getBirthDate(), oldDoctor.getBirthDate())) {
                oldDoctor.setBirthDate(doctor.getBirthDate());
            }

            if (doctor.getPesel() != null && !Objects.equals(doctor.getPesel(), oldDoctor.getPesel())) {
                oldDoctor.setPesel(doctor.getPesel());
            }

            if (doctor.getPhoneNumber() != null && !Objects.equals(doctor.getPhoneNumber(), oldDoctor.getPhoneNumber())) {
                oldDoctor.setPhoneNumber(doctor.getPhoneNumber());
            }


            doctorRepository.save(oldDoctor);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Doctor with this doctorId: %d", doctorId);
            throw new DataSaveException(message);
        }
    }

    public void updateByPesel(Doctor doctor) {
        String pesel = doctor.getPesel();
        try {
            Doctor oldDoctor = doctorRepository.findByPesel(pesel).orElseThrow(() -> {
                String message = String.format("%s with this PESEL: %s doesn't exist in database!", doctor.getClass().getName(), pesel);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            doctor.setDoctorId(oldDoctor.getDoctorId());

            if (doctor.getFirstName() != null && !Objects.equals(doctor.getFirstName(), oldDoctor.getFirstName())) {
                oldDoctor.setFirstName(doctor.getFirstName());
            }

            if (doctor.getLastName() != null && !Objects.equals(doctor.getLastName(), oldDoctor.getLastName())) {
                oldDoctor.setLastName(doctor.getLastName());
            }

            if (doctor.getBirthDate() != null && !Objects.equals(doctor.getBirthDate(), oldDoctor.getBirthDate())) {
                oldDoctor.setBirthDate(doctor.getBirthDate());
            }

            if (doctor.getPesel() != null && !Objects.equals(doctor.getPesel(), oldDoctor.getPesel())) {
                oldDoctor.setPesel(doctor.getPesel());
            }

            if (doctor.getPhoneNumber() != null && !Objects.equals(doctor.getPhoneNumber(), oldDoctor.getPhoneNumber())) {
                oldDoctor.setPhoneNumber(doctor.getPhoneNumber());
            }

            doctorRepository.save(oldDoctor);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Doctor with this PESEL: %s", pesel);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!doctorRepository.existsByDoctorId(id)) {
                String message = String.format("Cannot delete Doctor with doctorId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            doctorRepository.deleteByDoctorId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Doctor with this doctorId: %d", id);
            throw new DeleteException(message);
        }
    }

    @Transactional
    public void deleteByPesel(String PESEL) {
        try {
            if (!doctorRepository.existsByPesel(PESEL)) {
                String message = String.format("Cannot delete Doctor with PESEL: %s! Object doesn't exist in database!", PESEL);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            doctorRepository.deleteByPesel(PESEL);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Doctor with this PESEL: %s", PESEL);
            throw new DeleteException(message);
        }
    }

    public boolean existsByPesel(String pesel) {
        return doctorRepository.existsByPesel(pesel);
    }
}
