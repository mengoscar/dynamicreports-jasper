/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2011 Ricardo Mariaca
 * http://dynamicreports.sourceforge.net
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.dynamicreports.report.components.googlecharts.geomap;

import net.sf.dynamicreports.design.base.DRDesignGroup;
import net.sf.dynamicreports.design.constant.EvaluationTime;
import net.sf.dynamicreports.design.constant.ResetType;
import net.sf.dynamicreports.design.transformation.DesignTransformAccessor;
import net.sf.dynamicreports.jasper.components.googlecharts.GoogleChartsExtensionsRegistryFactory;
import net.sf.dynamicreports.jasper.components.googlecharts.geomap.GeoMapPrintElement;
import net.sf.dynamicreports.jasper.components.googlecharts.geomap.StandardGeoMapComponent;
import net.sf.dynamicreports.jasper.transformation.ConstantTransform;
import net.sf.dynamicreports.jasper.transformation.JasperTransformAccessor;
import net.sf.dynamicreports.report.components.CustomComponentTransform;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class GeoMapTransform implements CustomComponentTransform<DRIGeoMap, DRIDesignGeoMap> {

	public boolean isTransform(Object component) {
		return component instanceof DRIGeoMap || component instanceof DRIDesignGeoMap;
	}

	public DRIDesignGeoMap designComponent(DesignTransformAccessor accessor, DRIGeoMap geoMap, ResetType resetType, DRDesignGroup resetGroup) {
		DRDesignGeoMap designGeoMap = new DRDesignGeoMap();
		designGeoMap.setEvaluationTime(accessor.getComponentTransform().evaluationTimeFromResetType(resetType));
		designGeoMap.setEvaluationGroup(resetGroup);
		return designGeoMap;
	}

	public JRComponentElement jasperComponent(JasperTransformAccessor accessor, DRIDesignGeoMap geoMap) {
		StandardGeoMapComponent jrGeoMap = new StandardGeoMapComponent();
		EvaluationTime evaluationTime = geoMap.getEvaluationTime();
		jrGeoMap.setEvaluationTime(ConstantTransform.evaluationTime(evaluationTime));
		if (evaluationTime != null && evaluationTime.equals(EvaluationTime.GROUP) && geoMap.getEvaluationGroup() != null) {
			jrGeoMap.setEvaluationGroup(accessor.getGroupTransform().getGroup(geoMap.getEvaluationGroup()).getName());
		}

		JRDesignComponentElement jrComponent = new JRDesignComponentElement();
		jrComponent.setComponent(jrGeoMap);
		jrComponent.setComponentKey(new ComponentKey(GoogleChartsExtensionsRegistryFactory.NAMESPACE, "jr", GeoMapPrintElement.GEOMAP_ELEMENT_NAME));

		return jrComponent;
	}

}