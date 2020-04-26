

package io.token.rpc;

import io.token.proto.common.security.SecurityProtos;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_AuthenticationContext extends AuthenticationContext {

  private final String onBehalfOf;

  private final boolean customerInitiated;

  private final SecurityProtos.Key.Level keyLevel;

  private final SecurityProtos.CustomerTrackingMetadata customerTrackingMetadata;

  AutoValue_AuthenticationContext(
      @Nullable String onBehalfOf,
      boolean customerInitiated,
      SecurityProtos.Key.Level keyLevel,
      SecurityProtos.CustomerTrackingMetadata customerTrackingMetadata) {
    this.onBehalfOf = onBehalfOf;
    this.customerInitiated = customerInitiated;
    if (keyLevel == null) {
      throw new NullPointerException("Null keyLevel");
    }
    this.keyLevel = keyLevel;
    if (customerTrackingMetadata == null) {
      throw new NullPointerException("Null customerTrackingMetadata");
    }
    this.customerTrackingMetadata = customerTrackingMetadata;
  }

  @Nullable
  @Override
  String getOnBehalfOf() {
    return onBehalfOf;
  }

  @Override
  boolean getCustomerInitiated() {
    return customerInitiated;
  }

  @Override
  SecurityProtos.Key.Level getKeyLevel() {
    return keyLevel;
  }

  @Override
  SecurityProtos.CustomerTrackingMetadata getCustomerTrackingMetadata() {
    return customerTrackingMetadata;
  }

  @Override
  public String toString() {
    return "AuthenticationContext{"
         + "onBehalfOf=" + onBehalfOf + ", "
         + "customerInitiated=" + customerInitiated + ", "
         + "keyLevel=" + keyLevel + ", "
         + "customerTrackingMetadata=" + customerTrackingMetadata
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AuthenticationContext) {
      AuthenticationContext that = (AuthenticationContext) o;
      return ((this.onBehalfOf == null) ? (that.getOnBehalfOf() == null) : this.onBehalfOf.equals(that.getOnBehalfOf()))
           && (this.customerInitiated == that.getCustomerInitiated())
           && (this.keyLevel.equals(that.getKeyLevel()))
           && (this.customerTrackingMetadata.equals(that.getCustomerTrackingMetadata()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (onBehalfOf == null) ? 0 : onBehalfOf.hashCode();
    h$ *= 1000003;
    h$ ^= customerInitiated ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= keyLevel.hashCode();
    h$ *= 1000003;
    h$ ^= customerTrackingMetadata.hashCode();
    return h$;
  }

}
