<?php

namespace Demact\ApiBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * EntityBase
 */
abstract class EntityBase
{
    /**
     * @var integer
     */
    private $id;
    
    /**
     * @var integer
     */
    private $mobileId;

    /**
     * @var \DateTime
     */
    private $sync_uDate;
	
	/**
	 * @var boolean
	 */
	private $sync_dTag;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }
    
    /**
     * Set id
     *
     * @param integer $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }
    
    /**
     * Get local id
     *
     * @return integer 
     */
    public function getMobileId()
    {
        return $this->mobileId;
    }
    
    /**
     * Set local id
     *
     * @param integer $localId
     */
    public function setMobileId($mobileId)
    {
        $this->mobileId = $mobileId;
    }

    /**
     * Set sync_uDate
     *
     * @param \DateTime $syncUDate
     * @return EntityBase
     */
    public function setSyncUDate($syncUDate)
    {
        $this->sync_uDate = $syncUDate;
    
        return $this;
    }

    /**
     * Get sync_uDate
     *
     * @return \DateTime 
     */
    public function getSyncUDate()
    {
        return $this->sync_uDate;
    }
    /**
     * @var boolean
     */
    private $sync_dtag;


    /**
     * Set sync_dtag
     *
     * @param boolean $syncDtag
     * @return EntityBase
     */
    public function setSyncDtag($syncDtag)
    {
        $this->sync_dtag = $syncDtag;
    
        return $this;
    }

    /**
     * Get sync_dtag
     *
     * @return boolean 
     */
    public function getSyncDtag()
    {
        return $this->sync_dtag;
    }
    
    
    /**
     * Convert to string
     *
     * @return string 
     */
    public function toString() 
    {
    	return '{ id='. $this->getId() . ' mobileId=' . $this->getMobileId() .'}';
    	
    	//", GUID="+this.serverId+", data=" + this.data +  ", date_update=" + Concept.toDate(this.sync_uDate) + ", flag_delete=" + this.sync_dtag + " }"
    }
}
