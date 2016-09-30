package com.agilepro.persistence.repository.notification;

import java.util.List;

import com.agilepro.persistence.entity.notification.MailTemplateDefinitionEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface IMailTemplateDefinitionRepository extends IWebutilsRepository<MailTemplateDefinitionEntity>
{
	@RestrictBySpace
	public List<MailTemplateDefinitionEntity> fetchAllMailDefinition();
}
