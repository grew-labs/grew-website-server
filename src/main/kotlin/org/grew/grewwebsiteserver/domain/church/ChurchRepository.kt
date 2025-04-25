package org.grew.grewwebsiteserver.domain.church

import org.grew.grewwebsiteserver.domain.church.entity.Church
import org.springframework.data.repository.CrudRepository

interface ChurchRepository: CrudRepository<Church, Long> {
    fun findByName(name: String): Church?
}