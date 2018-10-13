package hr.fer.zemris.optjava.dz2;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.AbstractColorMap;
import org.jzy3d.colors.colormaps.IColorMap;

public class Red extends AbstractColorMap implements IColorMap{

	@Override
	public Color getColor(double x, double y, double z, double zMin, double zMax) {
		double rel_value = processRelativeZValue(z, zMin, zMax);
        
        float b = (float) colorComponentRelative( rel_value, 1f, 1.0f, 0.5f);
        float v = (float) colorComponentRelative( rel_value, 1f, 0.5f, 1.0f );
        float r = (float) colorComponentRelative( rel_value, 0.5f, 1.0f, 1.0f );
        
        return new Color( r, v, b );
		
	}

}
