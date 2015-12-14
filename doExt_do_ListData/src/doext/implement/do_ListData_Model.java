package doext.implement;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;
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

	private JSONArray data;

	public do_ListData_Model() throws Exception {
		super();
		data = new JSONArray();
	}

	@Override
	public void loadSync(String _content) throws Exception {
		super.loadSync(_content);
		loadModel(_content);
	}

	@Override
	public void load(String _content) throws Exception {
		super.load(_content);
		loadModel(_content);
	}

	private void loadModel(String _content) throws Exception {
		if (data == null) {
			data = new JSONArray(_content);
		} else {
			JSONArray _newData = new JSONArray(_content);
			addData(_newData, -1);
		}
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
	public boolean invokeSyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
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
	public boolean invokeAsyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
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
	public void addData(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONArray _data = DoJsonHelper.getJSONArray(_dictParas, "data");
		int _index = DoTextHelper.strToInt(DoJsonHelper.getString(_dictParas, "index", ""), -1);
		addData(_data, _index);
	}

	private void addData(JSONArray _data, int _index) throws JSONException {
		if (_index != -1 && _index <= data.length() - 1) {
			// 把原数组中从 _index开始的数据保存一份
			JSONArray _array = new JSONArray();
			for (int i = _index; i < data.length(); i++) {
				Object _obj = data.get(i);
				_array.put(_obj);
			}

			for (int i = 0; i < _data.length(); i++) {
				Object _obj = _data.get(i);
				data.put(i + _index, _obj);
			}

			int _len = _data.length();
			for (int i = 0; i < _array.length(); i++) {
				Object _obj = _array.get(i);
				data.put(i + _len + _index, _obj);
			}

		} else {
			for (int i = 0; i < _data.length(); i++) {
				Object _obj = _data.get(i);
				data.put(_obj);
			}
		}
	}

	@Override
	public void addOne(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		Object _data = DoJsonHelper.get(_dictParas, "data");
		int _index = DoTextHelper.strToInt(DoJsonHelper.getString(_dictParas, "index", ""), -1);
		if (_index != -1 && _index <= this.data.length() - 1) {
			// 把原数组中从 _index开始的数据保存一份
			JSONArray _array = new JSONArray();
			for (int i = _index; i < data.length(); i++) {
				Object _obj = data.get(i);
				_array.put(_obj);
			}
			data.put(_index, _data);
			for (int i = 0; i < _array.length(); i++) {
				Object _obj = _array.get(i);
				data.put(i + _index + 1, _obj);
			}

		} else {
			this.data.put(_data);
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
	public void getCount(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		_invokeResult.setResultInteger(data.length());
	}

	/**
	 * 获取某一行数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void getData(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONArray _data = new JSONArray();
		JSONArray _indexs = DoJsonHelper.getJSONArray(_dictParas, "indexs");
		int _len = this.data.length();
		if (_len == 0) {
			JSONArray _array = new JSONArray();
			for (int i = 0; i < _indexs.length(); i++) {
				_array.put(JSONObject.NULL);
			}
			_invokeResult.setResultArray(_array);
			return;
		}

		for (int i = 0; i < _indexs.length(); i++) {
			int _index = _indexs.getInt(i);
			if (_index == -1) { // 表示取末尾的数据
				_data.put(this.data.get(_len - 1));
				continue;
			}
			if (_len > _index) {
				_data.put(i, this.data.get(_index));
			} else {
				_data.put(i, JSONObject.NULL);
			}
		}
		_invokeResult.setResultArray(_data);
	}

	@Override
	public void getOne(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// index : 表示要读取的位置，从0开始; 如果 index参数为空或者越界, 则返回数组最后一个元素（如果数组为空则返回null）

		if (this.data.length() == 0) {
			_invokeResult.setResultValue(JSONObject.NULL);
			return;
		}
		int _index = DoJsonHelper.getInt(_dictParas, "index", -1);
		if (_index < 0) {
			_index = 0;
		}

		if (_index != 0 && _index > this.data.length() - 1) {
			_index = this.data.length() - 1;
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
	public void removeAll(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		this.data = new JSONArray();
	}

	@Override
	public void getRange(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// fromIndex : 起始索引位置（必需项），从0开始;
		// toIndex : 截止索引位置(可选项)，从0开始; 如果为空，则表示数组的最后一个元素位置

		int fromIndex = DoJsonHelper.getInt(_dictParas, "fromIndex", -1);
		int toIndex = DoJsonHelper.getInt(_dictParas, "toIndex", this.data.length() - 1);
		
		if(fromIndex > this.data.length() - 1){
			throw new Exception("fromIndex = "+ fromIndex +" 参数值非法！");
		}
		
		if(toIndex < fromIndex){
			throw new Exception("fromIndex 必须小于或等于  toIndex！");
		}
		
		if (fromIndex < 0) {
			fromIndex = 0;
		}

		if (toIndex < 0) {
			toIndex = 0;
		}
		if (toIndex >= this.data.length()) {
			toIndex = this.data.length() - 1;
		}
		if (this.data.length() == 0) {
			JSONArray _array = new JSONArray();
			for (int i = fromIndex; i <= toIndex; i++) {
				_array.put(JSONObject.NULL);
			}
			_invokeResult.setResultArray(_array);
			return;
		}

		JSONArray _data = new JSONArray();
		for (int i = fromIndex; i <= toIndex; i++) {
			Object _value = this.data.get(i);
			if (_value != null) {
				_data.put(this.data.get(i));
			} else {
				_data.put(JSONObject.NULL);
			}
		}
		_invokeResult.setResultArray(_data);
	}

	@Override
	public void removeRange(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// fromIndex : 起始索引位置（必需项），从0开始;
		// toIndex : 截止索引位置(可选项)，从0开始; 如果为空，则表示数组的最后一个元素位置
		if (this.data.length() == 0) {
			return;
		}

		int fromIndex = DoJsonHelper.getInt(_dictParas, "fromIndex", -1);
		int toIndex = DoJsonHelper.getInt(_dictParas, "toIndex", this.data.length() - 1);

		if(fromIndex > this.data.length() - 1){
			throw new Exception("fromIndex = "+ fromIndex +" 参数值非法！");
		}
		
		if(toIndex < fromIndex){
			throw new Exception("fromIndex 必须小于或等于  toIndex！");
		}
		
		if (fromIndex < 0) {
			fromIndex = 0;
		}

		if (toIndex < 0) {
			toIndex = 0;
		}
		if (toIndex >= this.data.length()) {
			toIndex = this.data.length() - 1;
		}

		JSONArray _newData = new JSONArray();
		int _len = this.data.length();
		for (int i = 0; i < _len; i++) {
			if (i < fromIndex || i > toIndex) {
				_newData.put(this.data.get(i));
			}
		}
		this.data = _newData;
	}

	/**
	 * 删除特定行的对象；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void removeData(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.length() == 0) {
			return;
		}
		JSONArray _indexs = DoJsonHelper.getJSONArray(_dictParas, "indexs");
		Set<Integer> _list = new HashSet<Integer>();
		JSONArray _newData = new JSONArray();
		for (int i = 0; i < _indexs.length(); i++) {
			int _index = _indexs.getInt(i);
			if (_index < 0) {
				_index = 0;
			}

			if (_index != 0 && _index > this.data.length() - 1) {
				_index = this.data.length() - 1;
			}
			_list.add(_index);
		}

		int _len = this.data.length();
		for (int i = 0; i < _len; i++) {
			if (!_list.contains(i)) {
				_newData.put(this.data.get(i));
			}
		}

		this.data = _newData;
	}

	/**
	 * 更新数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void updateOne(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if (this.data.length() == 0) {
			return;
		}
		int _index = DoJsonHelper.getInt(_dictParas, "index", -1);
		Object _data = DoJsonHelper.get(_dictParas, "data");
		if (_index < 0 || _index > this.data.length() - 1) {
			return;
		}
		this.data.put(_index, _data);
	}

	@Override
	public int getCount() {
		return data.length();
	}

	@Override
	public Object getData(int _index) throws JSONException {
		return this.data.get(_index);
	}

	@Override
	public Object getJsonData() throws Exception {
		return data;
	}

	@Override
	public void setData(int _index, Object _data) throws JSONException {
		this.data.put(_index, _data);
	}

	@Override
	public String serialize() throws Exception {
		return this.data.toString();
	}

	@Override
	public Object unSerialize(String _str) throws Exception {
		return DoJsonHelper.loadDataFromText(_str);
	}
}