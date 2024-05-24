/**
 * 
 */
package co.edu.usco.lcms.librerias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class CompararTipoUaa {

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	public boolean tipoUaa(int tipoUaaCodigo) {
		
		String codigoTipoUaa = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String[] codigoTipoUaaArray = codigoTipoUaa.split(",");

		for (int x = 0; x <= codigoTipoUaaArray.length; x++) {
			if (codigoTipoUaaArray[x].equals(String.valueOf(tipoUaaCodigo))) {
				return true;
			}
		}
		return false;
	}
}
