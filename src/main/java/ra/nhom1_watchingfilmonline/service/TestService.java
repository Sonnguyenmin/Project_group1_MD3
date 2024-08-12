//package ra.nhom1_watchingfilmonline.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ra.nhom1_watchingfilmonline.model.entity.Test;
//import ra.nhom1_watchingfilmonline.repository.TestDao;
//
//import java.util.List;
//
//@Service
//public class TestService {
//    @Autowired
//    private TestDao testDao;
//
//    @Autowired
//    private UploadService uploadService;
//
//
//
//    public List<Test> findAll() {
//        return testDao.findAll();
//    }
//
//
//    public Test findById(Integer id) {
//        return testDao.findById(id);
//    }
//
//
//    public void save(Test Test) {
//
//
//        Test test = Test.builder()
//                .id(getId())
//                .name(getName())
//                .build();
//        if (Test.getId() == null) {
//            film.setImage(uploadService.uploadFileToServer(filmRequest.getMultipartFile()));
//        } else {
//            if (filmRequest.getMultipartFile() != null && filmRequest.getMultipartFile().getSize() > 0) {
//                film.setImage(uploadService.uploadFileToServer(filmRequest.getMultipartFile()));
//            } else {
//                film.setImage(testDao.getImageById(filmRequest.getId()));
//            }
//        }
//        testDao.save(film);
//    }
//
//
//    public void delete(Integer id) {
//        testDao.delete(id);
//    }
//}
