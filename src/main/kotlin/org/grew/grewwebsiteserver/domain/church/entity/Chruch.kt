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
    val name: String,

    @Column(name = "pastor_name", length = 100)
    val pastorName: String? = null,

    @Column(name = "website_url", length = 255)
    val websiteUrl: String? = null,

    @Column(length = 255)
    val address: String? = null,

    @Column(length = 100)
    val email: String? = null,

    @Column(length = 50)
    val phone: String? = null,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(name = "worship_times", columnDefinition = "TEXT")
    val worshipTimes: String? = null,

    @Column(name = "location_lat", length = 20)
    val locationLat: String? = null,

    @Column(name = "location_lng", length = 20)
    val locationLng: String? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: EntityStatus = EntityStatus.ACTIVE
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
