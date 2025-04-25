package org.grew.grewwebsiteserver.domain.church.entity

import jakarta.persistence.*
import org.grew.grewwebsiteserver.common.EntityStatus
import java.time.LocalDateTime

@Entity
@Table(name = "churches")
data class Church(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(name = "pastor_name", length = 100)
    var pastorName: String? = null,

    @Column(name = "website_url", length = 255)
    var websiteUrl: String? = null,

    @Column(length = 255)
    var address: String? = null,

    @Column(length = 100)
    var email: String? = null,

    @Column(length = 50)
    var phone: String? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "worship_times", columnDefinition = "TEXT")
    var worshipTimes: String? = null,

    @Column(name = "location_lat")
    var locationLat: Double? = null,

    @Column(name = "location_lng")
    var locationLng: Double? = null,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    var updatedBy: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: EntityStatus = EntityStatus.ACTIVE
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
