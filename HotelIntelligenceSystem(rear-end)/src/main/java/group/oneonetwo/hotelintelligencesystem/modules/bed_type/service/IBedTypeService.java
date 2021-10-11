package group.oneonetwo.hotelintelligencesystem.modules.bed_type.service;

import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;

public interface IBedTypeService {
    BedTypePO selectOneById(String id);

    BedTypePO add(BedTypeVO bedTypeVO);

    BedTypeVO selectOneByIdReturnVO(String id);

    BedTypePO save(BedTypeVO bedTypeVO);

    Integer deleteById(String id);

}
