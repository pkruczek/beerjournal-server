package com.beerjournal.datamodel.repository.mongo;

import com.beerjournal.datamodel.entity.BottleEntity;
import com.beerjournal.datamodel.entity.CanEntity;
import com.beerjournal.datamodel.entity.CapEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.LabelEntity;
import com.beerjournal.datamodel.model.Bottle;
import com.beerjournal.datamodel.model.Can;
import com.beerjournal.datamodel.model.Cap;
import com.beerjournal.datamodel.model.CollectableObject;
import com.beerjournal.datamodel.model.Label;

public class CollectableObjectsMapper {
	public static CollectableObjectEntity getEntity(CollectableObject collectibleObject) {
		CollectableObjectEntity result = null;
		
		if (collectibleObject instanceof Bottle) {
			Bottle bottle = (Bottle)collectibleObject;
			result = new BottleEntity(bottle.id, bottle.ownerID, bottle.brewery, bottle.volume);
		} else if (collectibleObject instanceof Can) {
			Can can = (Can)collectibleObject;
			result = new CanEntity(can.id, can.ownerID, can.brewery, can.volume);
		} else if (collectibleObject instanceof Cap) {
			Cap cap = (Cap)collectibleObject;
			result = new CapEntity(cap.id, cap.ownerID, cap.brewery);
		} else if (collectibleObject instanceof Label) {
			Label label = (Label)collectibleObject;
			result = new LabelEntity(label.id, label.ownerID, label.brewery);
		}
		
		return result;
	}
	
	public static CollectableObject getObject(CollectableObjectEntity entity) {
		CollectableObject result = null;
		
		if (entity instanceof BottleEntity) {
			BottleEntity bottle = (BottleEntity)entity;
			result = new Bottle(bottle.getOwnerID(), bottle.getBrewery(), bottle.getVolume());
		} else if (entity instanceof CanEntity) {
			CanEntity can = (CanEntity)entity;
			result = new Can(can.getOwnerID(), can.getBrewery(), can.getVolume());
		} else if (entity instanceof CapEntity) {
			CapEntity cap = (CapEntity)entity;
			result = new Cap(cap.getOwnerID(), cap.getBrewery());
		} else if (entity instanceof LabelEntity) {
			LabelEntity label = (LabelEntity)entity;
			result = new Label(label.getOwnerID(), label.getBrewery());
		}
		
		return result;
	}
}
