package doext.implement;

import java.util.ArrayList;
import java.util.List;

import core.helper.DoTextHelper;
import core.helper.jsonparse.DoJsonNode;
import core.helper.jsonparse.DoJsonValue;
import core.interfaces.DoIListData;
import core.interfaces.DoIScriptEngine;
import core.interfaces.datamodel.DoIDataSource;
import core.object.DoInvokeResult;
import doext.define.do_ListData_IMethod;
import doext.define.do_ListData_MAbstract;

/**
 * 自定义扩展API组件Model实现，继承do_ListData_MAbstract抽象类，并实现do_ListData_IMethod接口方法；
 * #如何调用组件自定义事件？可以通过如下方法触发事件：
 * this.model.getEventCenter().fireEvent(_messageName, jsonResult);
 * 参数解释：@_messageName字符串事件名称，@jsonResult传递事件参数对象； 获取DoInvokeResult对象方式new
 * DoInvokeResult(this.getUniqueKey());
 */
public class do_ListData_Model extends do_ListData_MAbstract implements do_ListData_IMethod, DoIListData, DoIDataSource {

	private List<DoJsonValue> data;

	public do_ListData_Model() throws Exception {
		super();
		data = new ArrayList<DoJsonValue>();
	}

	/**
	 * 同步方法，JS脚本调用该组件对象方法时会被调用，可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V）
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public boolean invokeSyncMethod(String _methodName, DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if ("addData".equals(_methodName)) {
			addData(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("addOne".equals(_methodName)) {
			addOne(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("updateOne".equals(_methodName)) {
			updateOne(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("getCount".equals(_methodName)) {
			getCount(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("getData".equals(_methodName)) {
			getData(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("getOne".equals(_methodName)) {
			getOne(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("getRange".equals(_methodName)) {
			getRange(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}

		if ("removeAll".equals(_methodName)) {
			removeAll(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}

		if ("removeRange".equals(_methodName)) {
			removeRange(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}

		if ("removeData".equals(_methodName)) {
			removeData(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		return super.invokeSyncMethod(_methodName, _dictParas, _scriptEngine, _invokeResult);
	}

	/**
	 * 异步方法（通常都处理些耗时操作，避免UI线程阻塞），JS脚本调用该组件对象方法时会被调用， 可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V）
	 * @_scriptEngine 当前page JS上下文环境
	 * @_callbackFuncName 回调函数名 #如何执行异步方法回调？可以通过如下方法：
	 *                    _scriptEngine.callback(_callbackFuncName,
	 *                    _invokeResult);
	 *                    参数解释：@_callbackFuncName回调函数名，@_invokeResult传递回调函数参数对象；
	 *                    获取DoInvokeResult对象方式new
	 *                    DoInvokeResult(this.getUniqueKey());
	 */
	@Override
	public boolean invokeAsyncMethod(String _methodName, DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		// ...do something
		return super.invokeAsyncMethod(_methodName, _dictParas, _scriptEngine, _callbackFuncName);
	}

	/**
	 * 增加数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void addData(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		List<DoJsonValue> _data = _dictParas.getOneArray("data");
		int _index = DoTextHelper.strToInt(_dictParas.getOneText("index", ""), -1);
		if (_index != -1 && _index <= this.data.size() - 1) {
			this.data.addAll(_index, _data);
		} else {
			this.data.addAll(_data);
		}
	}

	@Override
	public void addOne(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		DoJsonValue _data = _dictParas.getOneValue("data");
		int _index = DoTextHelper.strToInt(_dictParas.getOneText("index", ""), -1);
		if (_index != -1 && _index <= this.data.size() - 1) {
			this.data.add(_index, _data);
		} else {
			this.data.add(_data);
		}
	}

	/**
	 * 获取元素个数；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void getCount(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		_invokeResult.setResultInteger(data.size());
	}

	/**
	 * 获取某一行数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void getData(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}
		List<String> _indexs = _dictParas.getOneTextArray("indexs");
		List<DoJsonValue> _data = new ArrayList<DoJsonValue>();
		for (int i = 0; i < _indexs.size(); i++) {
			int _index = DoTextHelper.strToInt(_indexs.get(i), 0);
			DoJsonValue _value = this.data.get(_index);
			if (_value != null) {
				_data.add(_value);
			}
		}
		_invokeResult.setResultArray(_data);
	}

	@Override
	public void getOne(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}

		int _index = DoTextHelper.strToInt(_dictParas.getOneText("index", ""), -1);
		if (_index < 0) {
			_index = 0;
		}

		if (_index != 0 && _index > this.data.size() - 1) {
			_index = this.data.size() - 1;
		}
		_invokeResult.setResultValue(this.data.get(_index));
	}

	/**
	 * 清空数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void removeAll(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		this.data.clear();
	}

	@Override
	public void getRange(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}
		int _startIndex = _dictParas.getOneInteger("startIndex", -1);
		int _length = _dictParas.getOneInteger("length", -1);
		if (_startIndex < 0) {
			_startIndex = 0;
		}

		if (_startIndex != 0 && _startIndex > this.data.size() - 1) {
			_startIndex = this.data.size() - 1;
		}

		if (_length < 0) {
			_length = 0;
		}
		if (_length >= this.data.size()) {
			_length = this.data.size();
		}

		List<DoJsonValue> _data = new ArrayList<DoJsonValue>();
		for (int i = _startIndex; i < _length; i++) {
			DoJsonValue _value = this.data.get(i);
			if (_value != null) {
				_data.add(this.data.get(i));
			}
		}
		_invokeResult.setResultArray(_data);
	}

	@Override
	public void removeRange(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}
		int _startIndex = _dictParas.getOneInteger("startIndex", -1);
		int _length = _dictParas.getOneInteger("length", -1);
		if (_startIndex < 0) {
			_startIndex = 0;
		}

		if (_startIndex != 0 && _startIndex > this.data.size() - 1) {
			_startIndex = this.data.size() - 1;
		}

		if (_length < 0) {
			_length = 0;
		}
		if (_length >= this.data.size()) {
			_length = this.data.size();
		}

		List<DoJsonValue> _data = new ArrayList<DoJsonValue>();
		for (int i = _startIndex; i < _length; i++) {
			DoJsonValue _value = this.data.get(i);
			if (_value != null) {
				_data.add(_value);
			}
		}
		this.data.removeAll(_data);
	}

	/**
	 * 删除特定行的对象；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void removeData(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}
		List<String> _indexs = _dictParas.getOneTextArray("indexs");
		List<DoJsonValue> _data = new ArrayList<DoJsonValue>();
		for (int i = 0; i < _indexs.size(); i++) {
			int _index = DoTextHelper.strToInt(_indexs.get(i), 0);
			if (_index < 0) {
				_index = 0;
			}

			if (_index != 0 && _index > this.data.size() - 1) {
				_index = this.data.size() - 1;
			}

			DoJsonValue _value = this.data.get(_index);
			if (_value != null) {
				_data.add(_value);
			}
		}
		this.data.removeAll(_data);
	}

	/**
	 * 更新数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void updateOne(DoJsonNode _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.size() == 0) {
			return;
		}
		int _index = _dictParas.getOneInteger("index", -1);
		String _data = _dictParas.getOneText("data", "");
		if (_index < 0) {
			_index = 0;
		}

		if (_index != 0 && _index > this.data.size() - 1) {
			_index = this.data.size() - 1;
		}

		DoJsonValue _value = new DoJsonValue();
		_value.loadDataFromText(_data);
		this.data.remove(_index);
		this.data.add(_index, _value);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getData(int _index) {
		return this.data.get(_index);
	}

	@Override
	public DoJsonValue getJsonData() throws Exception {
		DoJsonValue _jsonValue = new DoJsonValue();
		_jsonValue.setArray(data);
		return _jsonValue;
	}

	@Override
	public void setData(int _index, Object _data) {
		if (_data instanceof DoJsonValue) {
			this.data.add(_index, (DoJsonValue) _data);
		}
	}

	@Override
	public String serialize() throws Exception {
		DoJsonValue _jsonValue = new DoJsonValue();
		_jsonValue.setArray(data);
		return _jsonValue.exportToText(false);
	}

	@Override
	public Object unSerialize(String _str) throws Exception {
		DoJsonValue _value = new DoJsonValue();
		_value.loadDataFromText(_str);
		return _value;
	}
}