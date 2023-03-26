// noinspection JSUnusedGlobalSymbols

/**
 * 常用工具类 - 类型判断
 *
 * @author bianyun
 * @date   2022/6/11
 */


/**
 * 检查参数值是否为 null
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 null
 */
export function isNull(value) {
  return value === null
}

/**
 * 检查参数值是否为 undefined
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 undefined
 */
export function isUndefined(value) {
  return 'undefined' === typeof value
}

/**
 * 检查参数值是否为 null 或 undefined
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 null 或 undefined
 */
export function isNil(value) {
  return value == null
}

/**
 * 检查参数值是否为 Number 类型（不考虑特殊的 Number类型值 NaN）
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 Number 类型
 */
export function isNumber(value) {
  return 'number' === typeof value && !isNaN(value)
}

/**
 * 检查参数值是否为整数
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为整数
 */
export function isInteger(value) {
  return Number.isInteger(value)
}

/**
 * 检查参数值是否为 String 类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 String 类型
 */
export function isString(value){
  return value != null && typeof value.valueOf() === "string"
}

/**
 * 检查参数值是否为 Boolean 类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 Boolean 类型
 */
export function isBoolean(value) {
  return 'boolean' === typeof value
}

/**
 * 检查参数值是否为 Function 类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 Function 类型
 */
export function isFunction(value) {
  return 'function' === typeof value
}

/**
 * 检查参数值是否为 Date 类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 Date 类型
 */
export function isDate(value) {
  return Object.prototype.toString.call(value) === '[object Date]'
}

/**
 * 检查参数值是否为对象类型（即 typeof 返回结果为 object 且不为数组类型，不为 null）
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为对象类型
 */
export function isObject(value) {
  return 'object' === typeof value && !Array.isArray(value) && value !== null
}

/**
 * 检查参数值是否为 RegExp 对象类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 RegExp 对象类型
 */
export function isRegExp(value) {
  return Object.prototype.toString.call(value) === '[object RegExp]'
}

/**
 * 检查参数值是否为 BigInt 类型
 *
 * 注：BigInt 为 ECMAScript 2020 新增类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 BigInt 类型
 */
export function isBigInt(value) {
  return 'bigint' === typeof value
}

/**
 * 检查参数值是否为 Symbol 类型
 *
 * 注：Symbol 为 ECMAScript 2015 新增类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值是否为 Symbol 类型
 */
export function isSymbol(value) {
  return 'symbol' === typeof value
}

/**
 * 将值转换为布尔类型
 *
 * @param value - 参数值
 * @returns {boolean} 参数值转换为后布尔类型后的值
 */
export function toBoolean(value) {
  if (isNil(value)) {
    return false
  } else if (isBoolean(value)) {
    return value
  }  else if (isString(value)) {
    return value.toLowerCase() === 'true'
  } else {
    return value.toString().toLowerCase() === 'true'
  }
}

/**
 * 将值转换为布尔类型
 *
 * @param {string} type - 转换的目标类型
 * @param value - 参数值
 * @returns {boolean | string | number} 参数值转换为后布尔类型后的值
 */
export function convertToType(type, value) {
  if (isNil(type)) {
    throw new Error('type must not be null')
  }

  if (isNil(value)) {
    return value
  }

  if (type.toLowerCase() === 'boolean') {
    return toBoolean(value)
  } else if (type.toLowerCase() === 'string') {
    return value.toString()
  } else if (type.toLowerCase() === 'number') {
    return parseInt(value)
  } else {
    throw new Error('unknown type: ' + type)
  }
}


