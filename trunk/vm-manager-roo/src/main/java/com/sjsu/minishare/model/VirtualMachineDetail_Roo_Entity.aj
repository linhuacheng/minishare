// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import com.sjsu.minishare.model.VirtualMachineDetail;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect VirtualMachineDetail_Roo_Entity {
    
    declare @type: VirtualMachineDetail: @Entity;
    
    @PersistenceContext
    transient EntityManager VirtualMachineDetail.entityManager;
    
    @Version
    @Column(name = "version")
    private Integer VirtualMachineDetail.version;
    
    public Integer VirtualMachineDetail.getVersion() {
        return this.version;
    }
    
    public void VirtualMachineDetail.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void VirtualMachineDetail.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void VirtualMachineDetail.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            VirtualMachineDetail attached = VirtualMachineDetail.findVirtualMachineDetail(this.machineId);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void VirtualMachineDetail.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void VirtualMachineDetail.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public VirtualMachineDetail VirtualMachineDetail.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VirtualMachineDetail merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager VirtualMachineDetail.entityManager() {
        EntityManager em = new VirtualMachineDetail().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long VirtualMachineDetail.countVirtualMachineDetails() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VirtualMachineDetail o", Long.class).getSingleResult();
    }
    
    public static List<VirtualMachineDetail> VirtualMachineDetail.findAllVirtualMachineDetails() {
        return entityManager().createQuery("SELECT o FROM VirtualMachineDetail o", VirtualMachineDetail.class).getResultList();
    }
    
    public static VirtualMachineDetail VirtualMachineDetail.findVirtualMachineDetail(Integer machineId) {
        if (machineId == null) return null;
        return entityManager().find(VirtualMachineDetail.class, machineId);
    }
    
    public static List<VirtualMachineDetail> VirtualMachineDetail.findVirtualMachineDetailEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VirtualMachineDetail o", VirtualMachineDetail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
