package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ParamCentrosOpcDao;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;

@Service(value = "ParamCentrosOpcService")
public class ParamCentrosOpcServiceImpl implements ParamCentrosOpcService {
	//private static Logger logger = LoggerFactory.getLogger(ParamCentrosOpcServiceImpl.class);
	//private static Logger logger = Logger.getLogger(ParamCentrosOpcServiceImpl.class);
	@Autowired
	private ParamCentrosOpcDao paramCentrosOpcDao;

	@Override
	public List<ParamCentrosOpc> findAll(ParamCentrosOpc paramCentrosOpc)
			throws Exception {
		return this.paramCentrosOpcDao.findAll(paramCentrosOpc);
	}

	/*@Override
	public ParamCentrosOpc findOne(ParamCentrosOpc paramCentrosOpc) throws Exception {
		ParamCentrosOpc paramCentrosOpcRes = null;
		List<ParamCentrosOpc> listParamCentrosOpc = this.paramCentrosOpcDao.findAll(paramCentrosOpc);
		if (!listParamCentrosOpc.isEmpty()){
			List<String> opcHabil = null;
			for (ParamCentrosOpc paramCOpc : listParamCentrosOpc){
				if (null == opcHabil){
					paramCentrosOpcRes = paramCOpc;
					opcHabil = new ArrayList<String>(Arrays.asList(paramCOpc.getOpcHabil().split(",")));
				} else {
					String[] opc = paramCOpc.getOpcHabil().split(",");
					for(String op : opc){
						if (!opcHabil.contains(op)){
							opcHabil.add(op);
						}
					}
				}
			}
			StringBuilder result = new StringBuilder();
			for(String string : opcHabil) {
				result.append(string);
				result.append(",");
			}
			if (result.length() > 0 ){
				result.substring(0, result.length() - 1);
			}
			paramCentrosOpcRes.setOpcHabil(result.toString());
		}
		return paramCentrosOpcRes;
	}*/

	@Override
	public ParamCentrosOpc findOne(ParamCentrosOpc paramCentrosOpc) throws Exception {
		ParamCentrosOpc paramCentrosOpcRes = null;
		List<ParamCentrosOpc> listParamCentrosOpc = this.paramCentrosOpcDao.findAllConcat(paramCentrosOpc);
		if (!listParamCentrosOpc.isEmpty()){
			paramCentrosOpcRes = listParamCentrosOpc.get(0);
		}
		return paramCentrosOpcRes;
	}

	@Override
	public Long findAllCont(ParamCentrosOpc paramCentrosOpc) throws Exception {
		return this.paramCentrosOpcDao.findAllCont(paramCentrosOpc);
	}
}
