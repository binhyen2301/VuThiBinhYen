package com.haivn.controller;

import com.haivn.common_api.Students;
import com.haivn.common_api.SubjectStudent;
import com.haivn.common_api.Subjects;
import com.haivn.dto.ClassroomsDto;
import com.haivn.dto.StudentsDto;
import com.haivn.dto.SubjectDto;
import com.haivn.dto.SubjectStudentDto;
import com.haivn.mapper.StudentsMapper;
import com.haivn.mapper.SubjectMapper;
import com.haivn.mapper.SubjectStudentMapper;
import com.haivn.service.ClassroomsService;
import com.haivn.service.StudentsService;
import com.haivn.service.SubjectService;
import com.haivn.service.SubjectStudentService;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

@RequestMapping("/api/subject-student")
@RestController
@Slf4j
public class SubjectStudentController extends TextWebSocketHandler {

    private final SubjectStudentService subjectStudentService;
    private final ClassroomsService classroomsService;
    private final StudentsService studentsService;
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final StudentsMapper studentsMapper;
    private final SubjectStudentMapper subjectStudentMapper;
    @Value("${aam.upload.dir}")
    private String attachmentPath;

    public SubjectStudentController(SubjectStudentService subjectStudentService,
                                    ClassroomsService classroomsService,
                                    StudentsService studentsService,
                                    SubjectService subjectService,
                                    SubjectMapper subjectMapper,
                                    StudentsMapper studentsMapper, SubjectStudentMapper subjectStudentMapper) {
        this.subjectStudentService = subjectStudentService;
        this.classroomsService = classroomsService;
        this.studentsService = studentsService;
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
        this.studentsMapper = studentsMapper;
        this.subjectStudentMapper = subjectStudentMapper;
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<SubjectStudent> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Page<SubjectStudentDto> subjectStudentDtos = subjectStudentService.findByCondition(spec, pageable);

            result.put("result", subjectStudentDtos);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", e.getMessage());
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SubjectStudentDto subjectStudentDto = subjectStudentService.findById(id);
            result.put("result", subjectStudentDto);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", "Không tồn tại bản ghi");
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(subjectStudentService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        subjectStudentService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated SubjectStudentDto subjectStudentDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(subjectStudentService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        SubjectStudentDto subjectDto1 = subjectStudentService.update(subjectStudentDto, id);
        if (subjectDto1 != null) {
            result.put("result", subjectDto1);
            result.put("success", true);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/dang-ki-nhom/{id}")
    public ResponseEntity<Object> dangKiNhom(@RequestBody @Validated SubjectStudentDto subjectStudentDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(subjectStudentService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        SubjectStudentDto subjectDto1 = subjectStudentService.dangKiNhom(subjectStudentDto, id);
        if (subjectDto1 != null) {
            result.put("result", subjectDto1);
            result.put("success", true);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/cham-diem/{id}")
    public ResponseEntity<Object> chamDiem(@RequestBody @Validated SubjectStudentDto subjectStudentDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(subjectStudentService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        SubjectStudentDto subjectDto1 = subjectStudentService.chamDiem(subjectStudentDto, id);
        if (subjectDto1 != null) {
            result.put("result", subjectDto1);
            result.put("success", true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated SubjectStudentDto subjectStudentDto) {
        Map<String, Object> result = new HashMap<String, Object>();
        SubjectStudentDto subjectStudentDto1 = subjectStudentService.save(subjectStudentDto);
        if (subjectStudentDto1 != null) {
            result.put("result", subjectStudentDto1);
            result.put("success", true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/import-sv-to-subject/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> readExcel(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Subjects subjects = subjectMapper.toEntity(subjectService.findById(id));

        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream); // Sử dụng XSSFWorkbook nếu là file Excel định dạng .xlsx

            XSSFSheet sheet = workBook.getSheetAt(0);
            List<SubjectStudent> subjectStudentList = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (rowIndex == 0) {
                    continue;
                }
                String studentCode = getValue(row.getCell(1)).toString();
                String fullName = getValue(row.getCell(2)).toString();
                String dob = getValue(row.getCell(3)).toString();
                StudentsDto studentByCode = studentsService.findByUserName(studentCode);
                if (studentsService.existsByStudentCode(studentCode) && subjectStudentService.findBySubjectIdAndStudentId(id, studentByCode.getId()) != null) {
                    result.put("result", "Mã sinh viên " + studentCode + " đã tồn tại.Vui lòng kiểm tra ! ");
                    result.put("success", false);
                    return ResponseEntity.ok(result);
                } else if (studentsService.existsByStudentCode(studentCode) && subjectStudentService.findBySubjectIdAndStudentId(id, studentByCode.getId()) == null) {
                    SubjectStudent subjectStudent = new SubjectStudent();
                    subjectStudent.setSubjectId(subjects.getId());
                    subjectStudent.setStudentId(studentByCode.getId());
                    subjectStudentService.save(subjectStudentMapper.toDto(subjectStudent));
                } else {
                    ClassroomsDto classroomsDto = classroomsService.findByCode(getValue(row.getCell(4)).toString());
                    StudentsDto studentsDto = new StudentsDto();
                    studentsDto.setName(studentCode);
                    studentsDto.setStudentCode(studentCode);
                    studentsDto.setFullName(fullName);
                    studentsDto.setDob(dob);
                    studentsDto.setClassId(classroomsDto.getId());
                    Students students = studentsService.save(studentsDto);

                    SubjectStudent subjectStudent = new SubjectStudent();
                    subjectStudent.setSubjectId(subjects.getId());
                    subjectStudent.setStudentId(students.getId());
                    subjectStudentService.save(subjectStudentMapper.toDto(subjectStudent));
                }
            }
            result.put("result", "Đã upload file thành công !");
            result.put("success", true);
            // Trả về kết quả cho Angular
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }

    private Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            case _NONE:
                return null;
            default:
                break;
        }
        return null;
    }


    @PutMapping(value = "/nop-bai/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> nopBai(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SubjectStudentDto subjectStudentDto = subjectStudentService.uploadBT(file, id);
            result.put("result", subjectStudentDto);

        } catch (Exception e) {
            result.put("result", e.toString());
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/upload/getImage/{filename}", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        String path = "";
        File file = new File(attachmentPath + "/" + filename);
        if (file.exists()) {
            String contentType = "application/octet-stream";
            response.setContentType(contentType);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            // copy from in to out
            IOUtils.copy(in, out);
            out.close();
            in.close();
        } else {
            throw new FileNotFoundException();
        }
    }
}
