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

package net.sf.dynamicreports.jasper.components.googlecharts.geomap;

import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentCompiler;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class GeoMapCompiler implements ComponentCompiler {

	public void collectExpressions(Component component, JRExpressionCollector collector) {
		//GeoMapComponent geoMap = (GeoMapComponent) component;
		//collector.addExpression(geoMap.getLatitudeExpression());
	}

	public Component toCompiledComponent(Component component, JRBaseObjectFactory baseFactory) {
		GeoMapComponent geoMap = (GeoMapComponent) component;
		return new StandardGeoMapComponent(geoMap, baseFactory);
	}

	public void verify(Component component, JRVerifier verifier) {
		GeoMapComponent geoMap = (GeoMapComponent) component;

		EvaluationTimeEnum evaluationTime = geoMap.getEvaluationTime();
		if (evaluationTime == EvaluationTimeEnum.AUTO) {
			verifier.addBrokenRule("Auto evaluation time is not supported for geo maps", geoMap);
		} else if (evaluationTime == EvaluationTimeEnum.GROUP) {
			String evaluationGroup = geoMap.getEvaluationGroup();
			if (evaluationGroup == null || evaluationGroup.length() == 0) {
				verifier.addBrokenRule("No evaluation group set for geo map", geoMap);
			} else if (!verifier.getReportDesign().getGroupsMap().containsKey(evaluationGroup)) {
				verifier.addBrokenRule("Map evalution group \"" + evaluationGroup + " not found", geoMap);
			}
		}
	}
}