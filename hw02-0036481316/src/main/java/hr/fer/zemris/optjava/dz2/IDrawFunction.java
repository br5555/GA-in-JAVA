package hr.fer.zemris.optjava.dz2;

import org.jzy3d.plot3d.builder.Mapper;

public interface IDrawFunction extends IHFunction {

	/**
	 * Public getter function as {@linkplain org.jzy3d.plot3d.builder.Mapper}
	 * object
	 * 
	 * @return function as {@linkplain org.jzy3d.plot3d.builder.Mapper} object
	 */
	Mapper getMapper();

}
